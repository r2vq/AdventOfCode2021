import java.util.*

fun main() {
    val input = Scanner(System.`in`)
    val calls = input.next().split(",").map { it.toInt() }
    val boards = makeBoards(input)
    input.close()

    var winner: Board? = null
    var winningCall: Int? = null

    calls.forEach { call ->
        boards.forEach { board ->
            if (board.call(call)) {
                if (winner == null) {
                    winner = board
                    winningCall = call
                }
            }
        }
    }

    println("Winner:\n${winner}")
    println("Call: $winningCall")
    println("Unmarked Sum: ${winner?.unmarked()}")
    println("Score: ${(winner?.unmarked() ?: 0) * (winningCall ?: 0)}")

    println("Boards: ${boards.size}")
}

fun makeBoards(input: Scanner): MutableList<Board> {
    val boards: MutableList<Board> = mutableListOf()
    while (input.hasNext()) {
        (boards.lastOrNull()?.takeIf { it.hasSpace() } ?: Board().also { boards.add(it) })
            .run { add(input.nextInt()) }
    }
    return boards
}

class Board {
    private var count = 0
    private var currentRow = 0
    private var currentCol = 0
    private var _hasSpace = true
    private var alreadyWon = false
    private val rows = listOf<MutableList<Cell>>(
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
    )
    private val cols = listOf<MutableList<Cell>>(
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
    )
    private val grid = mutableMapOf<Int, Cell>()

    override fun toString(): String = buildString {
        var longestCell = 0
        rows.forEach { row ->
            row.forEach { cell ->
                val length = cell.value.toString().length + if (cell.isSelected) 1 else 0
                if (length > longestCell) {
                    longestCell = length
                }
            }
        }
        val rowString = rows.joinToString(separator = "\n") { row ->
            row.joinToString(separator = " ") { cell ->
                var output = ("*".takeIf { cell.isSelected } ?: "") + cell.value.toString()
                while (output.length < longestCell) output = " $output"
                output
            }
        }
        append(listOf("B", "I", "N", "G", "O").joinToString(separator = " ") {
            var output = it
            var isFront = false
            while (output.length < longestCell) output =
                if (isFront.also { isFront = !isFront }) " $output" else "$output "
            output
        })
        append("\n")
        append(rowString)
    }

    // not 27401
    // not 45792

    fun hasSpace(): Boolean = _hasSpace

    fun add(value: Int) {
        val cell = Cell(value, false, currentRow, currentCol)

        rows[currentRow].add(cell)
        cols[currentCol].add(cell)
        grid[value] = cell

        currentCol += 1
        if (currentCol >= 5) {
            currentCol = 0
            currentRow += 1

            if (currentRow >= 5) {
                currentRow = 0
                _hasSpace = false
            }
        }

        count += 1
    }

    fun unmarked() = grid
        .values
        .fold(0) { acc, curr -> acc + (curr.value.takeUnless { curr.isSelected } ?: 0) }

    fun call(value: Int): Boolean = grid[value]?.let { cell ->
        if (alreadyWon) {
            return false
        }
        cell.isSelected = true
        val rowBingo = rows[cell.row].fold(true) { acc, curr -> acc && curr.isSelected }
        val colBingo = cols[cell.col].fold(true) { acc, curr -> acc && curr.isSelected }
        val isWin = rowBingo || colBingo
        alreadyWon = isWin
        isWin
    } ?: false
}

data class Cell(
    val value: Int,
    var isSelected: Boolean = false,
    val row: Int,
    val col: Int,
)