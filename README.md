## KPSL - Performance Simulation Library

---

**Goal** : This library aims at providing resources to model networks to evaluate performances using simulations based
techniques. In particular, I developed this library for my personal use, to evaluate the impact of 
different machine learning based routing algorithms for content distribution.

---

### How to use ?

There are 2 main types of components :
- Service : That model server handling request
- Buffer : That model server queuing abilities

A typical model consist of a network of a Buffer-Service graph.

There are also special components allowing to create request arrival sources, ...

Example : Simulating the following architecture

```                 
                    q1  c1
               +--- ]]]]O ---+
           50% |             |     q3  c3
 Arrival ------+             +---> ]]]]O ---> (Exit the system)
    s1     50% |             |
               +--- ]]]]O ---+
                    q2  c2     
```

```JAVA

Component s1 = new BasicArrivalSource(new RoundRobinDispatcher(), 0.5D);

Buffer q1 = new BasicQueue(5, FIFOPolicy.getInstance(), new RoundRobinDispatcher());
Buffer q2 = new BasicQueue(5, FIFOPolicy.getInstance(), new RoundRobinDispatcher());
Buffer q3 = new BasicQueue(5, FIFOPolicy.getInstance(), new RoundRobinDispatcher());

Service c1 = new BasicService(3.0f, new RoundRobinDispatcher());
Service c2 = new BasicService(2.0f, new RoundRobinDispatcher());
Service c3 = new BasicService(2.0f, new RoundRobinDispatcher());

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
```
