package nms;

import api.CredentialProfile;

import java.util.concurrent.ExecutorService;

public class MetricScheduler
{
    public void scheduleTask(ExecutorService executor, long interval, CredentialProfile userProfile)
    {

        executor.execute(() ->
        {
            long startTime = System.currentTimeMillis();

            while (true)
            {
                long currentTime = System.currentTimeMillis();

                if ((currentTime - startTime) >= interval)
                {

                    startTime = currentTime;

                    new Poller().startPolling(userProfile);
                }

            }

        });
    }
}
