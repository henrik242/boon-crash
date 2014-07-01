boon-crash
==========

<pre><code>
✨  gradle build
:compileJava
Note: /path/to/boon-crash/src/main/java/JsonEncoder.java uses unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.
:compileGroovy UP-TO-DATE
:processResources UP-TO-DATE
:classes
:jar
:assemble
:compileTestJava UP-TO-DATE
:compileTestGroovy
:processTestResources UP-TO-DATE
:testClasses
:test
#
# A fatal error has been detected by the Java Runtime Environment:
#
#  SIGSEGV (0xb) at pc=0x0000000103070452, pid=12753, tid=22275
#
# JRE version: Java(TM) SE Runtime Environment (7.0_55-b13) (build 1.7.0_55-b13)
# Java VM: Java HotSpot(TM) 64-Bit Server VM (24.55-b03 mixed mode bsd-amd64 compressed oops)
# Problematic frame:
# j  org.boon.json.serializers.impl.InstanceSerializerImpl.serializeSubtypeInstance(Lorg/boon/json/serializers/JsonSerializerInternal;Ljava/lang/Object;Lorg/boon/primitive/CharBuf;)V+9
#
# Failed to write core dump. Core dumps have been disabled. To enable core dumping, try "ulimit -c unlimited" before starting Java again
#
# An error report file with more information is saved as:
# /path/to/boon-crash/hs_err_pid12753.log
#
# If you would like to submit a bug report, please visit:
#   http://bugreport.sun.com/bugreport/crash.jsp
#
:test FAILED

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':test'.
> Process 'Gradle Test Executor 2' finished with non-zero exit value 134

* Try:
Run with --stacktrace option to get the stack trace. Run with --info or --debug option to get more log output.

BUILD FAILED

Total time: 6.375 secs
</code></pre>
