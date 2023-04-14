fun main(args: Array<String>) {
    val httpResponse = HttpResponse("body", mapOf("1" to "a"))
    httpResponse.statusCode = 200
    httpResponse.statusCode2 = 300
}

/* create custom accessors
Since Kotlin can compute the hasBody value from the body property, it won’t generate a backing field for it.
* */
data class HttpResponse(val body: String, var headers: Map<String, String>) {
    val hasBody: Boolean
        get() = body.isNotBlank()

    // The current implementation is an endless recursive call
    // since we’re also setting this field (statusCode) inside the setter itself
    var statusCode: Int = 200
        set(value) {
            if (value in 100..599) {
                statusCode = value - 1
            }
        }

    // To avoid this endless recursion, we can use the backing field that Kotlin generates for this property
    var statusCode2: Int = 100
        set(value) {
            if (value in 100..599) {
                field = value
                println(field)
            }
        }
}