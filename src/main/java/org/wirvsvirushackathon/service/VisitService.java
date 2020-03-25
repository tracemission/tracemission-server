package org.wirvsvirushackathon.service;

import org.neo4j.driver.Driver;
import org.neo4j.driver.async.AsyncSession;
import org.neo4j.driver.async.ResultCursor;
import org.wirvsvirushackathon.model.Visit;
import org.wirvsvirushackathon.persistence.VisitQuery;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletionStage;

@Singleton
public class VisitService {

    @Inject
    Driver driver;

    public CompletionStage<Visit> checkIn(Visit visit) {
        AsyncSession session = driver.asyncSession();
        return session
                .writeTransactionAsync(tx -> tx
                        .runAsync(VisitQuery.CHECKIN_QUERY, VisitQuery.getParameterMap(visit))
                        .thenCompose(ResultCursor::singleAsync)
                )
                .thenApply(record -> Visit.from(record.get("r").asRelationship()))
                .thenCompose(persistedVisit -> session.closeAsync().thenApply( signal -> persistedVisit));
    }

    public CompletionStage<Visit> checkOut(Visit id) {
        AsyncSession session = driver.asyncSession();
        return session
                .writeTransactionAsync(tx -> tx
                        .runAsync(VisitQuery.CHECKOUT_QUERY, VisitQuery.getParameterMap(id))
                        .thenCompose(ResultCursor::singleAsync)
                )
                .thenApply(record -> Visit.from(record.get("r").asRelationship()))
                .thenCompose(persistedVisit -> session.closeAsync().thenApply( signal -> persistedVisit));
    }
}
