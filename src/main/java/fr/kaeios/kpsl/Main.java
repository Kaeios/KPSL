package fr.kaeios.kpsl;

import fr.kaeios.kpsl.api.Component;
import fr.kaeios.kpsl.api.Service;
import fr.kaeios.kpsl.api.queue.Buffer;
import fr.kaeios.kpsl.gui.views.MainView;
import fr.kaeios.kpsl.impl.queues.BasicQueue;
import fr.kaeios.kpsl.impl.queues.policies.FIFOPolicy;
import fr.kaeios.kpsl.impl.routing.RoundRobinDispatcher;
import fr.kaeios.kpsl.impl.services.BasicService;
import fr.kaeios.kpsl.gui.drawing.DrawingVisitor;
import fr.kaeios.kpsl.impl.sources.PeriodicArrivalSource;
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

    public static void main(String[] args) throws InterruptedException {
        // --- Queues (Buffers) ---
// DNS queue
        BasicQueue dnsQueue = new BasicQueue(10, FIFOPolicy.getInstance(), new RoundRobinDispatcher());

// Local server queues
        BasicQueue qLocal1 = new BasicQueue(5, FIFOPolicy.getInstance(), new RoundRobinDispatcher());
        BasicQueue qLocal2 = new BasicQueue(5, FIFOPolicy.getInstance(), new RoundRobinDispatcher());
        BasicQueue qLocal3 = new BasicQueue(5, FIFOPolicy.getInstance(), new RoundRobinDispatcher());
        BasicQueue qLocal4 = new BasicQueue(5, FIFOPolicy.getInstance(), new RoundRobinDispatcher());
        BasicQueue qLocal5 = new BasicQueue(5, FIFOPolicy.getInstance(), new RoundRobinDispatcher());

// Regional server queues
        BasicQueue qRegional1 = new BasicQueue(5, FIFOPolicy.getInstance(), new RoundRobinDispatcher());
        BasicQueue qRegional2 = new BasicQueue(5, FIFOPolicy.getInstance(), new RoundRobinDispatcher());
        BasicQueue qRegional3 = new BasicQueue(5, FIFOPolicy.getInstance(), new RoundRobinDispatcher());

// Origin server queue
        BasicQueue qOrigin = new BasicQueue(5, FIFOPolicy.getInstance(), new RoundRobinDispatcher());

// --- Services (Servers) ---
        BasicService dns = new BasicService(1.0f, new RoundRobinDispatcher(), 1);
        BasicService local1 = new BasicService(3.0f, new RoundRobinDispatcher(), 1);
        BasicService local2 = new BasicService(3.0f, new RoundRobinDispatcher(), 1);
        BasicService local3 = new BasicService(3.0f, new RoundRobinDispatcher(), 1);
        BasicService local4 = new BasicService(3.0f, new RoundRobinDispatcher(), 1);
        BasicService local5 = new BasicService(3.0f, new RoundRobinDispatcher(), 1);

        BasicService regional1 = new BasicService(2.0f, new RoundRobinDispatcher(), 1);
        BasicService regional2 = new BasicService(2.0f, new RoundRobinDispatcher(), 1);
        BasicService regional3 = new BasicService(2.0f, new RoundRobinDispatcher(), 1);

        BasicService origin = new BasicService(5.0f, new RoundRobinDispatcher(), 1);


        PeriodicArrivalSource s = new PeriodicArrivalSource(new RoundRobinDispatcher(), 0.2f);

// --- Connect DNS to Local Servers ---
        s.connectTo(dnsQueue);

        dnsQueue.connectTo(dns);
        dns.connectTo(qLocal1);
        dns.connectTo(qLocal2);
        dns.connectTo(qLocal3);
        dns.connectTo(qLocal4);
        dns.connectTo(qLocal5);

        qLocal1.connectTo(local1);
        qLocal2.connectTo(local2);
        qLocal3.connectTo(local3);
        qLocal4.connectTo(local4);
        qLocal5.connectTo(local5);

// --- Connect Local Servers to Regional Servers or Exit ---
        local1.connectTo(qRegional1); // exit system
        local2.connectTo(qRegional1);
        local3.connectTo(qRegional2);
        local4.connectTo(qRegional3);
        local5.connectTo(qRegional3); // exit system

        qRegional1.connectTo(regional1);
        qRegional2.connectTo(regional2);
        qRegional3.connectTo(regional3);

// Regional servers connect further
//        regional1.connectTo(null); // exit system
//        regional2.connectTo(null); // exit system
//        regional3.connectTo(null); // exit system

// Optionally, origin server if needed
        qOrigin.connectTo(origin); // origin server is leaf
//        origin.connectTo(null);    // exit system

        MainView view = new MainView();

        DrawingVisitor visitor = new DrawingVisitor(view);
        visitor.visit(s);

        view.disp();
        view.setVisible(true);

        for(double t = 0.0D; t <= 10.0D; t+=0.1D)
        {
            SimulationVisitor v2 = new SimulationVisitor(0.1D);

            v2.visit(s);

            view.renderView();

            Thread.sleep(500);
        }
    }

}
