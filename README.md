AkkaCTDStartup
========

This small project demonstrates a problem I've encountered writing actor Unit tests.
If I create an ActorRef using the CallingThreadDispatcher and then send a message to
the actor immediately after creation it actually runs using the default-dispatcher
rather than the CallingThreadDispatcher.  Sleeping for a few milliseconds between
creating the actor and sending the message makes he problem go away.

    > sbt test
    ...
    [info] DemoTests:
    [info] an actor 
    [info] - should run on the calling thread using the CallingThreadDispatcher
    [info] Passed: : Total 1, Failed 0, Errors 0, Passed 1, Skipped 0
    [ERROR] [10/05/2013 11:47:50.167] [default-akka.actor.default-dispatcher-3] [akka://default/user/$a] null
    org.scalatest.exceptions.TestFailedException
        at org.scalatest.Assertions$class.newAssertionFailedException(Assertions.scala:316)
        at demo.DemoTests.newAssertionFailedException(DemoTests.scala:8)
        at org.scalatest.Assertions$class.assert(Assertions.scala:311)
        at demo.DemoTests.assert(DemoTests.scala:8)
        at demo.DemoTests$Fubar$$anonfun$receive$1.applyOrElse(DemoTests.scala:12)
        at akka.actor.ActorCell.receiveMessage(ActorCell.scala:498)
        at akka.actor.ActorCell.invoke(ActorCell.scala:456)
        at akka.testkit.CallingThreadDispatcher.process$1(CallingThreadDispatcher.scala:251)
        at akka.testkit.CallingThreadDispatcher.runQueue(CallingThreadDispatcher.scala:284)
        at akka.testkit.CallingThreadDispatcher.dispatch(CallingThreadDispatcher.scala:208)
        at akka.actor.dungeon.Dispatch$class.sendMessage(Dispatch.scala:120)
        at akka.actor.ActorCell.sendMessage(ActorCell.scala:338)
        at akka.actor.UnstartedCell$$anonfun$replaceWith$1.apply$mcV$sp(RepointableActorRef.scala:186)
        at akka.actor.UnstartedCell$$anonfun$replaceWith$1.apply(RepointableActorRef.scala:182)
        at akka.actor.UnstartedCell$$anonfun$replaceWith$1.apply(RepointableActorRef.scala:182)
        at akka.actor.UnstartedCell.locked(RepointableActorRef.scala:268)
        at akka.actor.UnstartedCell.replaceWith(RepointableActorRef.scala:181)
        at akka.actor.RepointableActorRef.point(RepointableActorRef.scala:105)
        at akka.actor.ActorCell.handleSupervise(ActorCell.scala:596)
        at akka.actor.ActorCell.supervise(ActorCell.scala:588)
        at akka.actor.ActorCell.invokeAll$1(ActorCell.scala:432)
        at akka.actor.ActorCell.systemInvoke(ActorCell.scala:447)
        at akka.dispatch.Mailbox.processAllSystemMessages(Mailbox.scala:262)
        at akka.dispatch.Mailbox.run(Mailbox.scala:218)
        at akka.dispatch.ForkJoinExecutorConfigurator$AkkaForkJoinTask.exec(AbstractDispatcher.scala:386)
        at scala.concurrent.forkjoin.ForkJoinTask.doExec(ForkJoinTask.java:260)
        at scala.concurrent.forkjoin.ForkJoinPool$WorkQueue.runTask(ForkJoinPool.java:1339)
        at scala.concurrent.forkjoin.ForkJoinPool.runWorker(ForkJoinPool.java:1979)
        at scala.concurrent.forkjoin.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:107)

    [success] Total time: 10 s, completed Oct 5, 2013 11:47:50 AM


