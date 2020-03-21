package org.wirvsvirushackathon;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.neo4j.driver.Driver;
import org.neo4j.driver.async.*;
import org.neo4j.driver.*;

import java.net.URI;
import java.util.concurrent.CompletionStage;


@Path("fruits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FruitResource {

    @Inject
    Driver driver;

    @GET
    public CompletionStage<Response> get() {
        AsyncSession session = driver.asyncSession();
        return session
                .runAsync("MATCH (f:Fruit) RETURN f ORDER BY f.name")
                .thenCompose(cursor ->
                        cursor.listAsync(record -> Fruit.from(record.get("f").asNode()))
                )
                .thenCompose(fruits ->
                        session.closeAsync().thenApply(signal -> fruits)
                )
                .thenApply(Response::ok)
                .thenApply(Response.ResponseBuilder::build);
    }

    @POST
    public CompletionStage<Response> create(Fruit fruit) {
        AsyncSession session = driver.asyncSession();
        return session
                .writeTransactionAsync(tx -> tx
                        .runAsync("CREATE (f:Fruit {name: $name}) RETURN f", Values.parameters("name", fruit.getName()))
                        .thenCompose(ResultCursor::singleAsync)
                )
                .thenApply(record -> Fruit.from(record.get("f").asNode()))
                .thenCompose(persistedFruit -> session.closeAsync().thenApply(signal -> persistedFruit))
                .thenApply(persistedFruit -> Response
                        .created(URI.create("/fruits/" + persistedFruit.getId()))
                        .build()
                );
    }
}

