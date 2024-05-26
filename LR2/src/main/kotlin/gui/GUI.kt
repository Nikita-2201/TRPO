package gui

import cycle_list.CycleList
import factory.UserFactory
import types.users.UserType
import java.awt.Dimension
import java.awt.Font
import java.awt.event.ActionEvent
import java.io.FileNotFoundException
import javax.swing.*
import javax.xml.stream.XMLStreamException

class GUI : JPanel() {
    private val findBtn: JButton
    private val delBtn: JButton
    private val insertByIdBtn: JButton
    private val insertBtn: JButton
    private val sortBtn: JButton
    private val saveBtn: JButton
    private val loadBtn: JButton
    private val clearBtn: JButton
    private val findLabel: JLabel
    private val delLabel: JLabel
    private val typeLabel: JLabel
    private val insertLabel: JLabel
    private val createLabel: JLabel
    private val sortLabel: JLabel
    private val outTextField: JTextArea
    private val findByIdField: JTextField
    private val delByIdField: JTextField
    private val insertByIdField: JTextField
    private val typeList: JComboBox<*>
    var userFactory: UserFactory
    var userType: UserType?
    var cycleList: CycleList
    var defaultType = "Integer"
    val FILE_NAME_INTEGER = "CycleListInteger.dat"
    val FILE_NAME_FRACTION = "CycleListFraction.dat"

    init {
        userFactory = UserFactory()
        userType = userFactory.getBuilderByName(defaultType)
        cycleList = CycleList()

        val typeNameList = userFactory.getTypeNameList()
        val factoryListItemsItems = arrayOfNulls<String>(typeNameList.size)
        for (i in typeNameList.indices) {
            factoryListItemsItems[i] = typeNameList[i]
        }
        val factoryListItems = JComboBox<String>(factoryListItemsItems)
        factoryListItems.setSelectedItem(defaultType)

        findBtn = JButton("Найти")
        delBtn = JButton("Удалить")
        insertByIdBtn = JButton("Вставить")
        insertBtn = JButton("Сгенерировать")
        sortBtn = JButton("Сортировать")
        saveBtn = JButton("Сохранить")
        loadBtn = JButton("Загрузить")
        clearBtn = JButton("Очистить")
        outTextField = JTextArea(5, 5)
        typeList = factoryListItems
        findByIdField = JTextField(5)
        delByIdField = JTextField(5)
        insertByIdField = JTextField(5)
        findLabel = JLabel("Поиск по индексу")
        delLabel = JLabel("Удалить по индексу")
        typeLabel = JLabel("Тип данных")
        insertLabel = JLabel("Вставка по индексу")
        createLabel = JLabel("Сгенерировать данные")
        sortLabel = JLabel("Сортировка")
        outTextField.setEnabled(true)
        outTextField.setFont(Font("Arial", Font.BOLD, 14))
        preferredSize = Dimension(600, 563)
        setLayout(null)
        add(findBtn)
        add(delBtn)
        add(insertByIdBtn)
        add(outTextField)
        add(typeList)
        add(findByIdField)
        add(findLabel)
        add(delByIdField)
        add(delLabel)
        add(typeLabel)
        add(insertLabel)
        add(insertByIdField)
        add(insertBtn)
        add(createLabel)
        add(sortLabel)
        add(sortBtn)
        add(saveBtn)
        add(loadBtn)
        add(clearBtn)
        findBtn.setBounds(25, 135, 150, 25)
        delBtn.setBounds(25, 220, 150, 25)
        insertByIdBtn.setBounds(25, 315, 150, 25)
        outTextField.setBounds(190, 20, 345, 525)
        typeList.setBounds(25, 50, 150, 25)
        findByIdField.setBounds(25, 105, 150, 25)
        findLabel.setBounds(25, 85, 150, 20)
        delByIdField.setBounds(25, 190, 150, 25)
        delLabel.setBounds(25, 165, 150, 25)
        typeLabel.setBounds(25, 20, 150, 25)
        insertLabel.setBounds(25, 255, 150, 25)
        insertByIdField.setBounds(25, 280, 150, 25)
        insertBtn.setBounds(25, 435, 150, 20)
        createLabel.setBounds(25, 405, 145, 25)
        sortLabel.setBounds(25, 350, 100, 25)
        sortBtn.setBounds(25, 375, 150, 25)
        saveBtn.setBounds(25, 465, 150, 20)
        loadBtn.setBounds(25, 490, 150, 20)
        clearBtn.setBounds(25, 520, 150, 25)
        findBtn.addActionListener { e: ActionEvent? -> findNodeById() }
        delBtn.addActionListener { e: ActionEvent? -> deleteNodeById() }
        insertByIdBtn.addActionListener { e: ActionEvent? ->
            try {
                addNodeById()
            } catch (ex: InstantiationException) {
                throw RuntimeException(ex)
            }
        }
        insertBtn.addActionListener { e: ActionEvent? ->
            try {
                addNode()
            } catch (ex: InstantiationException) {
                throw RuntimeException(ex)
            }
        }
        sortBtn.addActionListener { e: ActionEvent? -> sortList() }
        saveBtn.addActionListener { e: ActionEvent? -> saveList() }
        loadBtn.addActionListener { e: ActionEvent? ->
            try {
                loadList()
            } catch (ex: XMLStreamException) {
                throw RuntimeException(ex)
            } catch (ex: FileNotFoundException) {
                throw RuntimeException(ex)
            }
        }
        clearBtn.addActionListener { e: ActionEvent? -> clearOutTextField() }
        typeList.addActionListener { e: ActionEvent -> selectTypeList(e) }
    }

    fun showMenu() {
        val frame = JFrame("MainMenu")
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        frame.contentPane.add(GUI())
        frame.pack()
        frame.isVisible = true
        frame.setResizable(false)
    }

    private fun selectTypeList(actionEvent: ActionEvent) {
        val box = actionEvent.source as JComboBox<*>
        val item = box.selectedItem as String
        if (defaultType !== item) {
            defaultType = item
            userType = userFactory.getBuilderByName(defaultType)
            cycleList = CycleList()
            setTextOnOutTextField()
        }
    }

    @Throws(InstantiationException::class)
    private fun addNode() {
        cycleList.add(userType!!.create())
        setTextOnOutTextField()
    }

    @Throws(InstantiationException::class)
    private fun addNodeById() {
        if (insertByIdField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Пустое поле! Введите значение!")
            return
        }
        cycleList.add(userType!!.create(), insertByIdField.getText().toInt())
        setTextOnOutTextField()
    }

    private fun deleteNodeById() {
        if (delByIdField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Пустое поле! Введите значение!")
            return
        }
        cycleList.remove(delByIdField.getText().toInt())
        setTextOnOutTextField()
    }

    private fun sortList() {
        cycleList.sort(userType!!.typeComparator)
        setTextOnOutTextField()
    }

    private fun findNodeById() {
        if (findByIdField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Пустое поле! Введите значение!")
            return
        }
        JOptionPane.showMessageDialog(
            null, "Найденное значение: " +
                    cycleList.getByIndex(findByIdField.getText().toInt())
        )
    }

    private fun saveList() {
        if (defaultType === "Integer") {
            cycleList.save(userType!!, FILE_NAME_INTEGER)
            JOptionPane.showMessageDialog(null, "Список успешно сохранен в \"$FILE_NAME_INTEGER\"!")
        } else {
            cycleList.save(userType!!, FILE_NAME_FRACTION)
            JOptionPane.showMessageDialog(null, "Список успешно сохранен в \"$FILE_NAME_FRACTION\"!")
        }
    }

    @Throws(XMLStreamException::class, FileNotFoundException::class)
    private fun loadList() {
        if (defaultType === "Integer") {
            cycleList.load(userType!!, FILE_NAME_INTEGER)
            JOptionPane.showMessageDialog(null, "Список успешно загружен!")
            setTextOnOutTextField()
        } else {
            cycleList.load(userType!!, FILE_NAME_FRACTION)
            JOptionPane.showMessageDialog(null, "Список успешно загружен!")
            setTextOnOutTextField()
        }
    }

    private fun clearOutTextField() {
        cycleList = CycleList()
        setTextOnOutTextField()
    }

    private fun setTextOnOutTextField() {
        outTextField.text = cycleList.toString()
    }
}
