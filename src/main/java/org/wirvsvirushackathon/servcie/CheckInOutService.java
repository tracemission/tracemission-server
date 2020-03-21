package org.wirvsvirushackathon.servcie;

import org.neo4j.driver.Driver;
import org.neo4j.driver.async.AsyncSession;
import org.neo4j.driver.async.ResultCursor;
import org.wirvsvirushackathon.model.CheckInOutData;
import org.wirvsvirushackathon.persistence.CheckInOutQuery;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletionStage;

@Singleton
public class CheckInOutService {

    @Inject
    Driver driver;

    public void checkIn(CheckInOutData data) {
        AsyncSession session = driver.asyncSession();
        CompletionStage<ResultCursor> res = session.writeTransactionAsync(tx -> tx
                        .runAsync(CheckInOutQuery.CHECKIN_QUERY, CheckInOutQuery.getParameterMap(data))
                );
        session.closeAsync();
    }

    public void checkOut(CheckInOutData data) {
        //TODO update database
    }
}
