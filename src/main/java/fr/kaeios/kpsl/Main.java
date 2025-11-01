package fr.kaeios.kpsl;

import fr.kaeios.kpsl.api.Component;
import fr.kaeios.kpsl.api.Service;
import fr.kaeios.kpsl.api.queue.Buffer;
import fr.kaeios.kpsl.impl.*;
import fr.kaeios.kpsl.impl.visitors.SimulationVisitor;

public class Main {

    static Component s1;

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
        s1 = new BasicArrivalSource(new RoundRobinDispatcher(), 0.5D);

        q1 = new BasicQueue(5, FIFOPolicy.getInstance(), new RoundRobinDispatcher());
        q2 = new BasicQueue(5, FIFOPolicy.getInstance(), new RoundRobinDispatcher());
        q3 = new BasicQueue(5, FIFOPolicy.getInstance(), new RoundRobinDispatcher());

        c1 = new BasicService(3.0f, new RoundRobinDispatcher());
        c2 = new BasicService(2.0f, new RoundRobinDispatcher());
        c3 = new BasicService(2.0f, new RoundRobinDispatcher());

        s1.connectTo(q1);
        s1.connectTo(q2);
        s1.connectTo(q3);

        q1.connectTo(c1);
        q2.connectTo(c2);
        q3.connectTo(c3);

        c1.connectTo(q3);
        c2.connectTo(q3);

        for(double t = 0.0D; t <= 10.0D; t+=0.1D)
        {
            SimulationVisitor visitor = new SimulationVisitor(0.1D);

            System.out.println(
                    "t=" + Math.round((t) * 100)/100.0
                            + ", q1 = " + q1.getPopulation().size()
                            + ", q2 = " + q2.getPopulation().size()
                            + ", q3 = " + q3.getPopulation().size()
                            + ", c1 = " + (c1.getCurrentRequest() == null ? "0" : "1")
                            + ", c2 = " + (c2.getCurrentRequest() == null ? "0" : "1")
                            + ", c3 = " + (c3.getCurrentRequest() == null ? "0" : "1")
            );

            visitor.visit(s1);
        }
    }

}
