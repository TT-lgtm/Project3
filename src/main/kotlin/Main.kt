import com.sun.jdi.connect.Connector.StringArgument
fun main(args: Array<String>) {
    var exitProgram = true
    do {
        print("Выберите номер задания для запуска (всего заданий 4 (1-4)): ")
        val inputTask = readln()
        val taskNum = inputTask.toInt()
        when (taskNum){
            1 -> taskNum1()
            2 -> taskNum2()
            3 -> taskNum3()
            4 -> taskNum4()
            else -> break
        }
        println("Хотите завершить работу программы?")
        val inputExit = readln()
        if(inputExit=="Да" || inputExit=="да" || inputExit=="Yes" || inputExit=="yes") {
            exitProgram = false
        }
        else {
            println("----------------------------------------")
        }
    } while (exitProgram)
}

//Камень Ножницы Бумага

fun taskNum1(){
    println("1 - Камень, 2 - Ножницы, 3 - Бумага")
    println("Ваш выбор:")
    val user = readLine()!!.toInt()

    val comp = (1..3).random()

    println("Компьютер выбрал: $comp")

    if (user == comp) {
        println("Ничья. Попробуйте снова.")
        return
    }

    if (user == 1 && comp == 2 ||
        user == 2 && comp == 3 ||
        user == 3 && comp == 1
    ) {
        println("Вы победили!")
    } else {
        println("Вы проиграли!")
    }
}

//Биграммный шифр Порты

fun taskNum2() {
    val alphabet = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЫЬЭЮЯ"

    println("Введите исходное сообщение (только буквы):")
    var message = readLine()!!.uppercase().replace(" ", "")

    println("Введите вспомогательный символ (например, Я):")
    val filler = readLine()!!.uppercase()[0]

    if (message.length % 2 != 0) {
        message += filler
    }

    println("Использовать типовую таблицу? (1 - да, 2 - случайная)")
    val choice = readLine()!!.toInt()

    val table = Array(32) { IntArray(32) }

    if (choice == 1) {
        var num = 1
        for (i in 0 until 32) {
            for (j in 0 until 32) {
                table[i][j] = num
                num++
            }
        }
    } else {
        val numbers = (1..999).shuffled().take(32 * 32).toMutableList()
        var index = 0
        for (i in 0 until 32) {
            for (j in 0 until 32) {
                table[i][j] = numbers[index]
                index++
            }
        }
    }


    val pairs = mutableListOf<String>()
    for (i in message.indices step 2) {
        pairs.add(message.substring(i, i + 2))
    }


    val encrypted = mutableListOf<String>()
    for (pair in pairs) {
        val row = alphabet.indexOf(pair[0])
        val col = alphabet.indexOf(pair[1])

        val num = table[row][col]

        val three = String.format("%03d", num)
        encrypted.add(three)
    }


    println("\nПары букв:")
    println(pairs.joinToString(" "))

    println("\nЗашифрованное сообщение:")
    println(encrypted.joinToString(" "))

    println("\nТаблица шифрования 32×32:")
    for (i in 0 until 32) {
        for (j in 0 until 32) {
            print(String.format("%03d ", table[i][j]))
        }
        println()
    }
}

//Шифр Виженера

fun taskNum3(){
    val alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"

    println("Введите исходное сообщение:")
    val message = readLine()!!.uppercase().replace(" ", "")

    println("Введите ключ:")
    val keyInput = readLine()!!.uppercase().replace(" ", "")

    println("Использовать типовую таблицу? (1 - да, 2 - случайная)")
    val typeChoice = readLine()!!.toInt()

    val table = Array(33) { CharArray(33) }

    if (typeChoice == 1) {
        for (i in 0 until 33) {
            for (j in 0 until 33) {
                table[i][j] = alphabet[(j + i) % 33]
            }
        }
    } else {
        // Случайная таблица
        val firstRow = alphabet.toList().shuffled()
        val firstRowStr = firstRow.joinToString("")

        for (i in 0 until 33) {
            for (j in 0 until 33) {
                table[i][j] = firstRow[(j + i) % 33]
            }
        }
    }

    // Повторяю ключ, чтобы он был длины сообщения
    val fullKey = StringBuilder()
    var ki = 0
    for (i in message.indices) {
        fullKey.append(keyInput[ki])
        ki++
        if (ki == keyInput.length) ki = 0
    }

    // Шифрую
    val encrypted = StringBuilder()
    for (i in message.indices) {
        val row = alphabet.indexOf(fullKey[i])
        val col = alphabet.indexOf(message[i])
        encrypted.append(table[row][col])
    }

    println("\nИсходное сообщение:")
    println(message)

    println("\nКлюч:")
    println(fullKey.toString())

    println("\nЗашифрованное сообщение:")
    println(encrypted.toString())

    println("\nТаблица Виженера 33×33:")
    for (i in 0 until 33) {
        for (j in 0 until 33) {
            print("${table[i][j]} ")
        }
        println()
    }
}

//Омофоническая замена

fun taskNum4() {
    // Алфавит-- пробел тоже шифруем
    val symbols = listOf(
        'А','Б','В','Г','Д','Е','Ж','З','И','Й','К','Л','М','Н','О','П',
        'Р','С','Т','У','Ф','Х','Ц','Ч','Ш','Щ','Ъ','Ы','Ь','Э','Ю','Я',
        ' '
    )

    // Вероятности (по условию). Пробел в конце-- 0.146
    val probs = mapOf(
        'А' to 0.069, 'Б' to 0.013, 'В' to 0.038, 'Г' to 0.014, 'Д' to 0.024,
        'Е' to 0.071, 'Ж' to 0.007, 'З' to 0.016, 'И' to 0.064, 'Й' to 0.010,
        'К' to 0.029, 'Л' to 0.039, 'М' to 0.027, 'Н' to 0.057, 'О' to 0.094,
        'П' to 0.026, 'Р' to 0.042, 'С' to 0.046, 'Т' to 0.054, 'У' to 0.023,
        'Ф' to 0.003, 'Х' to 0.008, 'Ц' to 0.005, 'Ч' to 0.012, 'Ш' to 0.006,
        'Щ' to 0.004, 'Ъ' to 0.001, 'Ы' to 0.015, 'Ь' to 0.013, 'Э' to 0.002,
        'Ю' to 0.005, 'Я' to 0.017, ' ' to 0.146
    )

    println("Омофоническая замена.")
    println("Выберите режим: 1 - зашифровать, 2 - расшифровать")
    val mode = readLine()!!.trim().toInt()

    // Сначала нужно создать таблицу: спросим тип
    println("Тип таблицы: 1 - типовая (по вероятностям), 2 - случайная")
    val typeChoice = readLine()!!.trim().toInt()

    // Вычислим целые количества кодов (сумма = 1000)
    data class Item(val ch: Char, val exact: Double, var base: Int, val frac: Double)
    val items = mutableListOf<Item>()
    var sumBase = 0
    for (ch in symbols) {
        val p = probs[ch] ?: 0.0
        val exact = p * 1000.0
        val base = exact.toInt() // floor
        val frac = exact - base
        items.add(Item(ch, exact, base, frac))
        sumBase += base
    }
    // Распределяем остаток по дробным частям
    var remainder = 1000 - sumBase
    if (remainder > 0) {
        val sortedByFrac = items.sortedByDescending { it.frac }
        var i = 0
        while (remainder > 0) {
            sortedByFrac[i % sortedByFrac.size].base += 1
            remainder--
            i++
        }
    }

    // Теперь base у каждого = количество кодов для символа
    val counts = items.associate { it.ch to it.base }

    // Создаём списки кодов (0..999), либо по порядку (типовая), либо случайно
    val allNumbers = (0..999).toMutableList()
    if (typeChoice == 2) {
        allNumbers.shuffle()
    }
    var ptr = 0
    val table = mutableMapOf<Char, MutableList<Int>>()
    for (ch in symbols) {
        val cnt = counts[ch] ?: 0
        val list = mutableListOf<Int>()
        for (k in 0 until cnt) {
            if (ptr < allNumbers.size) {
                list.add(allNumbers[ptr])
                ptr++
            }
        }
        table[ch] = list
    }

    // Для удобной дешифровки — карта номер -> символ
    val reverse = mutableMapOf<Int, Char>()
    for ((ch, codes) in table) {
        for (c in codes) {
            reverse[c] = ch
        }
    }

    if (mode == 1) {
        // Шифрование
        println("Введите исходное сообщение (будут использоваться только русские буквы и пробел):")
        val raw = readLine()!!.uppercase()
        // Оставим только символы, которые есть в таблице
        val message = StringBuilder()
        for (c in raw) {
            if (c in table.keys) message.append(c)
        }

        // Для каждого символа будем брать следующий код из его набора (чтобы не повторять пока есть)
        val nextIndex = mutableMapOf<Char, Int>()
        val encrypted = mutableListOf<String>()

        for (c in message.toString()) {
            val codes = table[c]!!
            if (codes.isEmpty()) {
                // защита: если для символа нет кодов (маловероятно), используем 000
                encrypted.add("000")
                continue
            }
            val idx = nextIndex.getOrDefault(c, 0)
            val code = codes[idx % codes.size]
            encrypted.add(code.toString().padStart(3, '0'))
            nextIndex[c] = idx + 1
        }

        // Выводим результат
        println("\nИсходное сообщение (отфильтровано):")
        println(message.toString())

        println("\nЗашифрованное сообщение (группы по 3 цифры):")
        println(encrypted.joinToString(" "))

        println("\nТаблица шифрозамен (символ : список кодов):")
        for (ch in symbols) {
            val codes = table[ch]!!
            val codesStr = codes.joinToString(" ") { it.toString().padStart(3, '0') }
            println("$ch: $codesStr")
        }
    } else {
        // Дешифровка
        println("Введите зашифрованное сообщение — группы по 3 цифры через пробел (например: 357 990 374):")
        val line = readLine()!!.trim()
        val parts = line.split(Regex("\\s+")).filter { it.isNotEmpty() }

        val decoded = StringBuilder()
        for (p in parts) {
            val num = p.toIntOrNull()
            if (num == null) {
                // если не число — пропускаем
                continue
            }
            val ch = reverse[num]
            if (ch != null) decoded.append(ch)
            else decoded.append('?') // неизвестный код
        }

        println("\nДешифрованное сообщение:")
        println(decoded.toString())

        println("\nТаблица шифрозамен (символ : список кодов):")
        for (ch in symbols) {
            val codes = table[ch]!!
            val codesStr = codes.joinToString(" ") { it.toString().padStart(3, '0') }
            println("$ch: $codesStr")
        }
    }
//Короткие замечания (важно для понимания)
//В типовой таблице коды идут подряд от 000 до 999, разделённые по количествам, полученным из вероятностей (сумма — 1000).

//В случайной таблице эти же 1000 чисел перемешиваются и распределяются по тем же количествам, поэтому все числа уникальны.

//При шифровании для одной буквы используются разные коды из её набора (циклически), чтобы уменьшить частотность.

//При дешифровке программа ждёт последовательность трёхзначных кодов, разделённых пробелами, и переводит их в символы по таблице.
}
