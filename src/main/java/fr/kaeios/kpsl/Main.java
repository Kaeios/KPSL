package fr.kaeios.kpsl;

import fr.kaeios.kpsl.api.Service;
import fr.kaeios.kpsl.api.queue.Buffer;
import fr.kaeios.kpsl.impl.*;

public class Main {

    static Buffer q1;
    static Service c1;

    static Buffer q2;
    static Service c2;

    static Buffer q3;
    static Service c3;

    /*
                   +--->  |||O  -------+
                   |   Local server 1  |
                   |                   |
                   +--->  |||O  -------+               +------>  |||O  -------+
                   |   Local server 2  |               |   Regional Server 1  |
                   |                   |               |                      |
                   +--->  |||O  -------+---------------+------>  |||O  -------+-------------------->  |||O  --------> (Exit System)
                   |   Local server 3  |  [Cache Miss] |   Regional Server 2  |  [Cache Miss]     Origin Server
                   |                   |               |                      |
    ---->  |||O  --+--->  |||O  -------+               +------>  |||O  -------+-------------> (Exit System)
      DNS Server   |  Local Server 4   |                   Regional Server 3     [Cache OK]
                   |                   |
                   +--->  |||O  -------+
                      Local Server 5   |
                                       |
                                       +---------------->  (Exit system)
                                         [Cache OK]

    */

    /*
    Client ---- Local Server --- Origin Server
     */

    public static void main(String[] args) {
        q1 = new BasicQueue(5, FIFOPolicy.getInstance(), RoundRobinDispatcher.getInstance());
        q2 = new BasicQueue(5, FIFOPolicy.getInstance(), RoundRobinDispatcher.getInstance());
        q3 = new BasicQueue(5, FIFOPolicy.getInstance(), RoundRobinDispatcher.getInstance());

        c1 = new BasicService(3.0f, RoundRobinDispatcher.getInstance());
        c2 = new BasicService(2.0f, RoundRobinDispatcher.getInstance());
        c3 = new BasicService(2.0f, RoundRobinDispatcher.getInstance());

        q1.connectTo(c1);
        q2.connectTo(c2);
        q3.connectTo(c3);

        c1.connectTo(q3);
        c2.connectTo(q3);

        q1.onArrival(new DummyRequest());
        q1.onArrival(new DummyRequest());
        q1.onArrival(new DummyRequest());

        q2.onArrival(new DummyRequest());
        q2.onArrival(new DummyRequest());
        q2.onArrival(new DummyRequest());
        q2.onArrival(new DummyRequest());
        q2.onArrival(new DummyRequest());

        for(double t = 0.0D; t <= 10.0D; t+=0.1D)
        {
            System.out.println(
                    "t=" + Math.round((t) * 100)/100.0
                            + ", q1 = " + q1.getPopulation().size()
                            + ", q2 = " + q2.getPopulation().size()
                            + ", q3 = " + q3.getPopulation().size()
                            + ", c1 = " + (c1.getCurrentRequest() == null ? "0" : "1")
                            + ", c2 = " + (c2.getCurrentRequest() == null ? "0" : "1")
                            + ", c3 = " + (c3.getCurrentRequest() == null ? "0" : "1")
            );

            q1.onTick(0.1D);
            q2.onTick(0.1D);
            c1.onTick(0.1D);
            c2.onTick(0.1D);
            q3.onTick(0.1D);
            c3.onTick(0.1D);
        }
    }

}
