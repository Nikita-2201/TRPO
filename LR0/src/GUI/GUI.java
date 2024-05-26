package GUI;

import CycleList.CycleList;
import Factory.UserFactory;
import Types.Users.UserType;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import javax.swing.*;
import javax.xml.stream.XMLStreamException;

public class GUI extends JPanel {
    private JButton findBtn;
    private JButton delBtn;
    private JButton insertByIdBtn;
    private JButton insertBtn;
    private JButton sortBtn;
    private JButton saveBtn;
    private JButton loadBtn;
    private JButton clearBtn;
    private JLabel findLabel;
    private JLabel delLabel;
    private JLabel typeLabel;
    private JLabel insertLabel;
    private JLabel createLabel;
    private JLabel sortLabel;
    private JTextArea outTextField;
    private JTextField findByIdField;
    private JTextField delByIdField;
    private JTextField insertByIdField;
    private JComboBox typeList;
    public UserFactory userFactory;
    public UserType userType;
    public CycleList cycleList;
    public String defaultType;

    public final String FILE_NAME_INTEGER = "CycleListInteger.dat";
    public final String FILE_NAME_FRACTION = "CycleListFraction.dat";

    public GUI() {
        defaultType = "Integer";
        userFactory = new UserFactory();
        userType = userFactory.getBuilderByName(defaultType);
        cycleList = new CycleList();
        JComboBox<String> factoryListItems = new JComboBox<>(UserFactory.getTypeNameList().toArray(new String[0]));

        findBtn = new JButton("Найти");
        delBtn = new JButton("Удалить");
        insertByIdBtn = new JButton("Вставить");
        insertBtn = new JButton("Сгенерировать");
        sortBtn = new JButton("Сортировать");
        saveBtn = new JButton("Сохранить");
        loadBtn = new JButton("Загрузить");
        clearBtn = new JButton("Очистить");
        outTextField = new JTextArea(5, 5);
        typeList = factoryListItems;
        findByIdField = new JTextField(5);
        delByIdField = new JTextField(5);
        insertByIdField = new JTextField(5);
        findLabel = new JLabel("Поиск по индексу");
        delLabel = new JLabel("Удалить по индексу");
        typeLabel = new JLabel("Тип данных");
        insertLabel = new JLabel("Вставка по индексу");
        createLabel = new JLabel("Сгенерировать данные");
        sortLabel = new JLabel("Сортировка");

        outTextField.setEnabled(true);
        outTextField.setFont(new Font("Arial", Font.BOLD, 14));

        setPreferredSize(new Dimension(600, 563));
        setLayout(null);

        add(findBtn);
        add(delBtn);
        add(insertByIdBtn);
        add(outTextField);
        add(typeList);
        add(findByIdField);
        add(findLabel);
        add(delByIdField);
        add(delLabel);
        add(typeLabel);
        add(insertLabel);
        add(insertByIdField);
        add(insertBtn);
        add(createLabel);
        add(sortLabel);
        add(sortBtn);
        add(saveBtn);
        add(loadBtn);
        add(clearBtn);

        findBtn.setBounds(25, 135, 150, 25);
        delBtn.setBounds(25, 220, 150, 25);
        insertByIdBtn.setBounds(25, 315, 150, 25);
        outTextField.setBounds(190, 20, 345, 525);
        typeList.setBounds(25, 50, 150, 25);
        findByIdField.setBounds(25, 105, 150, 25);
        findLabel.setBounds(25, 85, 150, 20);
        delByIdField.setBounds(25, 190, 150, 25);
        delLabel.setBounds(25, 165, 150, 25);
        typeLabel.setBounds(25, 20, 150, 25);
        insertLabel.setBounds(25, 255, 150, 25);
        insertByIdField.setBounds(25, 280, 150, 25);
        insertBtn.setBounds(25, 435, 150, 20);
        createLabel.setBounds(25, 405, 145, 25);
        sortLabel.setBounds(25, 350, 100, 25);
        sortBtn.setBounds(25, 375, 150, 25);
        saveBtn.setBounds(25, 465, 150, 20);
        loadBtn.setBounds(25, 490, 150, 20);
        clearBtn.setBounds(25, 520, 150, 25);

        findBtn.addActionListener(e -> findNodeById());
        delBtn.addActionListener(e -> deleteNodeById());
        insertByIdBtn.addActionListener(e -> {
            try {
                addNodeById();
            } catch (InstantiationException ex) {
                throw new RuntimeException(ex);
            }
        });
        insertBtn.addActionListener(e -> {
            try {
                addNode();
            } catch (InstantiationException ex) {
                throw new RuntimeException(ex);
            }
        });
        sortBtn.addActionListener(e -> sortList());
        saveBtn.addActionListener(e -> saveList());
        loadBtn.addActionListener(e -> {
            try {
                loadList();
            } catch (XMLStreamException ex) {
                throw new RuntimeException(ex);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
        clearBtn.addActionListener(e -> clearOutTextField());
        typeList.addActionListener(e -> selectTypeList(e));
    }

    public void showMenu() {
        JFrame frame = new JFrame("MainMenu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new GUI());
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }


    private void selectTypeList(ActionEvent actionEvent) {
        JComboBox box = (JComboBox) actionEvent.getSource();
        String item = (String) box.getSelectedItem();
        if (defaultType != item) {
            defaultType = item;
            userType = userFactory.getBuilderByName(defaultType);
            cycleList = new CycleList();
            setTextOnOutTextField();
        }
    }

    private void addNode() throws InstantiationException {
        cycleList.add(userType.create());
        setTextOnOutTextField();

    }

    private void addNodeById() throws InstantiationException {
        if (insertByIdField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Пустое поле! Введите значение!");
            return;
        }
        cycleList.add(userType.create(), Integer.parseInt(insertByIdField.getText()));
        setTextOnOutTextField();
    }

    private void deleteNodeById() {
        if (delByIdField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Пустое поле! Введите значение!");
            return;
        }
        cycleList.remove(Integer.parseInt(delByIdField.getText()));
        setTextOnOutTextField();
    }

    private void sortList() {
        cycleList.sort(userType.getTypeComparator());
        setTextOnOutTextField();
    }

    private void findNodeById() {
        if (findByIdField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Пустое поле! Введите значение!");
            return;
        }
        JOptionPane.showMessageDialog(null, "Найденное значение: " +
                cycleList.getByIndex(Integer.parseInt(findByIdField.getText())));
    }

    private void saveList() {
        if (defaultType == "Integer") {
            cycleList.save(userType, FILE_NAME_INTEGER);
            JOptionPane.showMessageDialog(null, "Список успешно сохранен в \"" + FILE_NAME_INTEGER + "\"!");
        } else {
            cycleList.save(userType, FILE_NAME_FRACTION);
            JOptionPane.showMessageDialog(null, "Список успешно сохранен в \"" + FILE_NAME_FRACTION + "\"!");
        }

    }

    private void loadList() throws XMLStreamException, FileNotFoundException {
        if (defaultType == "Integer") {
            cycleList.load(userType, FILE_NAME_INTEGER);
            JOptionPane.showMessageDialog(null, "Список успешно загружен!");
            setTextOnOutTextField();
        } else {
            cycleList.load(userType, FILE_NAME_FRACTION);
            JOptionPane.showMessageDialog(null, "Список успешно загружен!");
            setTextOnOutTextField();
        }
    }

    private void clearOutTextField() {
        cycleList = new CycleList();
        setTextOnOutTextField();
    }

    private void setTextOnOutTextField() {
        outTextField.setText(cycleList.toString());
    }
}
