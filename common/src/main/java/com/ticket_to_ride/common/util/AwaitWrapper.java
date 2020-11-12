package com.ticket_to_ride.common.util;

import java.util.concurrent.Semaphore;


public class AwaitWrapper<T> {
    private T resource;
    private Semaphore await;

    public AwaitWrapper() {
        resource = null;
        await = new Semaphore(1);
        await.acquireUninterruptibly();
    }

    public T waitOnResource() {
        if (resource == null) {
            try {
                await.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;
            }
        }
        return resource;
    }

    public void setResource(T resource) {
        this.resource = resource;
        if (await.availablePermits() == 0) {
            await.release();
        }
    }
}
