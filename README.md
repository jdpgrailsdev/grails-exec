## grails-exec 

A thin jar with **no dependencies** for executing Grails (with an isolated classpath) programatically (e.g from Maven or Gradle).

    import grails.exec.Executor
    import grails.exec.RootLoader

    // Setup the classpath for Grails
    def classpath = []

    grailsJars.each { path ->
        classpath << new URL(path)
    }

    // Create a root class loader
    def classloader = new RootLoader(classpath)

    def executor = new GrailsExecutor(classloader, null, "/a/grails/project")
    executor.execute("test-app", "integration some.package.*", "-Dsome.system.property=true")