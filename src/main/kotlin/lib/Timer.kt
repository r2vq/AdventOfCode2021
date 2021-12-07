package lib

@Suppress("unused")
class Timer {
    private val startTime = System.currentTimeMillis()
    private val chapters: MutableList<Chapter> = mutableListOf()
    private var endTime: Long? = null

    fun addChapter(name: String) {
        if (endTime == null) {
            chapters.add(name to System.currentTimeMillis())
        }
    }

    fun finish() {
        endTime = System.currentTimeMillis()
    }

    override fun toString(): String {
        var previousChapter = "start"
        var previousTime = startTime
        var longest = 0
        return chapters
            .map { chapter ->
                chapter.print(previousTime, previousChapter).also {
                    previousTime = chapter.time
                    previousChapter = chapter.name
                }
            }
            .plus(chapters.lastOrNull()?.print(previousTime, previousChapter) ?: "No times found!")
            .plus(endTime?.let { "Total time: ${it - startTime}ms" } ?: "Finish not called")
            .onEach { longest = longest.coerceAtLeast(it.length) }
            .map { "┃ ${it.pad(longest)} ┃" }
            .let { content ->
                val border = "".pad(longest + 1, padChar = "━")
                listOf(
                    "┏$border━┓",
                    "┃${"Timer".pad(longest + 2, centered = true)}┃",
                    "┣$border━┫",
                )
                    .plus(content)
                    .plusElement("┗$border━┛")
            }
            .joinToString(separator = "\n") { it }
    }

    private data class Chapter(
        val name: String,
        val time: Long,
    ) {
        fun print(previousTime: Long, previousName: String) = "\"$previousName\" => ${time.minus(previousTime)}ms"
    }

    private infix fun String.to(time: Long) = Chapter(
        name = this,
        time = time
    )

    private fun String.pad(size: Int, padChar: String = " ", centered: Boolean = false): String {
        var isBefore = false
        var transformed = this
        val startSize = length
        for (i in startSize until size) {
            when {
                centered -> transformed =
                    "${if (isBefore) padChar else ""}$transformed${if (!isBefore) padChar else ""}"
                        .also { isBefore = !isBefore }
                else -> transformed += padChar
            }
        }
        return transformed
    }
}