import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BankQueueSystem extends JFrame {

    //เก็บค่าคิวล่าสุดของแต่ละอัน
    private Map<String, Integer> queueNumbers;
    private Map<String, Queue<String>> queues;
    private JLabel queueLabelA, queueLabelB, queueLabelC, queueLabelD;
    private JLabel counterLabelA, counterLabelB, counterLabelC, counterLabelD;

    public BankQueueSystem() {
        queueNumbers = new HashMap<>();
        queues = new HashMap<>();
        for (String code : new String[]{"A", "B", "C", "D"}) {
            queueNumbers.put(code, 1); // เริ่มคิวแจกใหม่ที่ 1
            queues.put(code, new LinkedList<>());
        }

        setTitle("In3vert Bank Queue System");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for displaying queue information
        JPanel queuePanel = new JPanel();
        queuePanel.setLayout(new GridLayout(2, 2, 10, 10));
        queuePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        queueLabelA = createQueueLabel("A");
        queueLabelB = createQueueLabel("B");
        queueLabelC = createQueueLabel("C");
        queueLabelD = createQueueLabel("D");
        //คิวล่าสุดที่ให้แสดง
        queuePanel.add(queueLabelA);
        queuePanel.add(queueLabelB);
        queuePanel.add(queueLabelC);
        queuePanel.add(queueLabelD);

        //สร้างปุ่ม
        JPanel servicePanel = new JPanel();
        servicePanel.setLayout(new GridLayout(4, 1, 10, 10));

        String[] services = {
                "A: ฝาก ถอน โอน จ่ายบิล",
                "B: เปิดบัญชี/สมัครบัตร ATM",
                "C: แลกเปลี่ยน/โอนเงินต่างประเทศ",
                "D: สินเชื่อ/บริการอื่นๆ"
        };
        //ใช้ loop ในการสร้างปุ่ม
        for (String service : services) {
            JButton button = new JButton(service);
            button.addActionListener(new ServiceButtonListener(service.substring(0, 1))); // Pass "A", "B", "C", or "D"
            servicePanel.add(button);
        }

        // สร้างที่เรียกคิวของพนังงาน
        JPanel employeePanel = new JPanel();
        employeePanel.setLayout(new GridLayout(2, 2, 10, 10));

        counterLabelA = createCounterLabel("A");
        counterLabelB = createCounterLabel("B");
        counterLabelC = createCounterLabel("C");
        counterLabelD = createCounterLabel("D");
        //สร้างปุ่มของเเต่ละเคาน์เตอร์
        JButton callButtonA = createCallButton("A");
        JButton callButtonB = createCallButton("B");
        JButton callButtonC = createCallButton("C");
        JButton callButtonD = createCallButton("D");
        //จัดเรียงหน้าตา
        employeePanel.add(callButtonA);
        employeePanel.add(counterLabelA);
        employeePanel.add(callButtonB);
        employeePanel.add(counterLabelB);
        employeePanel.add(callButtonC);
        employeePanel.add(counterLabelC);
        employeePanel.add(callButtonD);
        employeePanel.add(counterLabelD);

        add(queuePanel, BorderLayout.NORTH);
        add(servicePanel, BorderLayout.CENTER);
        add(employeePanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JLabel createQueueLabel(String code) {
        JLabel label = new JLabel(code + ": 0");
        label.setFont(new Font("Serif", Font.BOLD, 24));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private JLabel createCounterLabel(String code) {
        JLabel label = new JLabel("เคาน์เตอร์ " + code + ": -");
        label.setFont(new Font("Serif", Font.PLAIN, 18));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private JButton createCallButton(String code) {
        JButton button = new JButton("เรียกคิว " + code);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callNextQueue(code);
            }
        });
        return button;
    }

    private void callNextQueue(String code) {
        Queue<String> queue = queues.get(code);
        if (!queue.isEmpty()) {
            String nextQueue = queue.poll(); // Remove the first element
            int displayQueueNumber = Integer.parseInt(nextQueue.substring(1));

            // อัปเดตเคาน์เตอร์และจำนวนคิวที่เหลือ
            switch (code) {
                case "A":
                    counterLabelA.setText("เคาน์เตอร์ A: " + nextQueue);
                    queueLabelA.setText("A: " + queue.size());
                    break;
                case "B":
                    counterLabelB.setText("เคาน์เตอร์ B: " + nextQueue);
                    queueLabelB.setText("B: " + queue.size());
                    break;
                case "C":
                    counterLabelC.setText("เคาน์เตอร์ C: " + nextQueue);
                    queueLabelC.setText("C: " + queue.size());
                    break;
                case "D":
                    counterLabelD.setText("เคาน์เตอร์ D: " + nextQueue);
                    queueLabelD.setText("D: " + queue.size());
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(this, "ไม่มีคิวในระบบสำหรับ " + code, "Queue Empty", JOptionPane.WARNING_MESSAGE);
        }
    }

    private class ServiceButtonListener implements ActionListener {
        private String serviceCode;

        public ServiceButtonListener(String serviceCode) {
            this.serviceCode = serviceCode;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int currentQueueNumber = queueNumbers.get(serviceCode);
            //สร้างคิว
            String queueNumber = serviceCode + currentQueueNumber;
            queueNumbers.put(serviceCode, currentQueueNumber + 1);
            queues.get(serviceCode).add(queueNumber);
            //อัฟเดต จน.คิว
            switch (serviceCode) {
                case "A":
                    queueLabelA.setText("A: " + queues.get("A").size());
                    break;
                case "B":
                    queueLabelB.setText("B: " + queues.get("B").size());
                    break;
                case "C":
                    queueLabelC.setText("C: " + queues.get("C").size());
                    break;
                case "D":
                    queueLabelD.setText("D: " + queues.get("D").size());
                    break;
            }

            JOptionPane.showMessageDialog(
                    BankQueueSystem.this,
                    "หมายเลขคิวของคุณคือ : " + queueNumber + "\nบริการ : " + ((JButton) e.getSource()).getText().substring(3),
                    "Queue",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        new BankQueueSystem();
    }
}
