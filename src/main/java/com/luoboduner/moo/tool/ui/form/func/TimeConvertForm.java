package com.luoboduner.moo.tool.ui.form.func;

import cn.hutool.core.swing.clipboard.ClipboardUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.luoboduner.moo.tool.util.UndoUtil;
import lombok.Getter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * <pre>
 * 时间转换
 * </pre>
 *
 * @author <a href="https://github.com/rememberber">RememBerBer</a>
 * @since 2019/9/7.
 */
@Getter
public class TimeConvertForm {
    private JTextField timestampTextField;
    private JComboBox unitComboBox;
    private JButton toLocalTimeButton;
    private JTextField gmtTextField;
    private JButton toTimestampButton;
    private JPanel timeConvertPanel;
    private JLabel currentTimestampLabel;
    private JLabel currentGmtLabel;
    private JButton copyGeneratedTimestampButton;
    private JButton copyGeneratedLocalTimeButton;
    private JButton copyCurrentGmtButton;
    private JButton copyCurrentTimestampButton;

    private static final Log logger = LogFactory.get();

    private static TimeConvertForm timeConvertForm;

    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private TimeConvertForm() {
        UndoUtil.register(this);
        toLocalTimeButton.addActionListener(e -> {
            try {
                long timeStamp = Long.parseLong(timestampTextField.getText());
                String unit = (String) unitComboBox.getSelectedItem();
                if ("秒(s)".equals(unit)) {
                    timeStamp = timeStamp * 1000;
                }
                String localTime = DateFormatUtils.format(new Date(timeStamp), TIME_FORMAT);
                gmtTextField.setText(localTime);
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error(ExceptionUtils.getStackTrace(ex));
                JOptionPane.showMessageDialog(timeConvertPanel, ex.getMessage(), "转换失败！", JOptionPane.ERROR_MESSAGE);
            }
        });
        toTimestampButton.addActionListener(e -> {
            try {
                String localTime = gmtTextField.getText();
                String unit = (String) unitComboBox.getSelectedItem();
                Date date = DateUtils.parseDate(localTime, TIME_FORMAT);
                long timeStamp = date.getTime();
                if ("秒(s)".equals(unit)) {
                    timeStamp = timeStamp / 1000;
                }
                timestampTextField.setText(String.valueOf(timeStamp));
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error(ExceptionUtils.getStackTrace(ex));
                JOptionPane.showMessageDialog(timeConvertPanel, ex.getMessage(), "转换失败！", JOptionPane.ERROR_MESSAGE);
            }
        });
        copyCurrentGmtButton.addActionListener(e -> ThreadUtil.execute(() -> {
            try {
                copyCurrentGmtButton.setEnabled(false);
                ClipboardUtil.setStr(currentGmtLabel.getText());
                JOptionPane.showMessageDialog(timeConvertPanel, "内容已经复制到剪贴板！", "复制成功", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                logger.error(e1);
            } finally {
                copyCurrentGmtButton.setEnabled(true);
            }
        }));
        copyCurrentTimestampButton.addActionListener(e -> ThreadUtil.execute(() -> {
            try {
                copyCurrentTimestampButton.setEnabled(false);
                ClipboardUtil.setStr(currentTimestampLabel.getText());
                JOptionPane.showMessageDialog(timeConvertPanel, "内容已经复制到剪贴板！", "复制成功", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                logger.error(e1);
            } finally {
                copyCurrentTimestampButton.setEnabled(true);
            }
        }));
        copyGeneratedTimestampButton.addActionListener(e -> ThreadUtil.execute(() -> {
            try {
                copyGeneratedTimestampButton.setEnabled(false);
                ClipboardUtil.setStr(timestampTextField.getText());
                JOptionPane.showMessageDialog(timeConvertPanel, "内容已经复制到剪贴板！", "复制成功", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                logger.error(e1);
            } finally {
                copyGeneratedTimestampButton.setEnabled(true);
            }
        }));
        copyGeneratedLocalTimeButton.addActionListener(e -> ThreadUtil.execute(() -> {
            try {
                copyGeneratedLocalTimeButton.setEnabled(false);
                ClipboardUtil.setStr(gmtTextField.getText());
                JOptionPane.showMessageDialog(timeConvertPanel, "内容已经复制到剪贴板！", "复制成功", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                logger.error(e1);
            } finally {
                copyGeneratedLocalTimeButton.setEnabled(true);
            }
        }));
    }

    public static TimeConvertForm getInstance() {
        if (timeConvertForm == null) {
            timeConvertForm = new TimeConvertForm();
        }
        return timeConvertForm;
    }

    public static void init() {
        timeConvertForm = getInstance();

        ThreadUtil.execute(() -> {
            while (true) {
                timeConvertForm.getCurrentTimestampLabel().setText(String.valueOf(System.currentTimeMillis() / 1000));
                timeConvertForm.getCurrentGmtLabel().setText(DateFormatUtils.format(new Date(), TIME_FORMAT));
                ThreadUtil.safeSleep(1000);
            }
        });

        if ("".equals(timeConvertForm.getTimestampTextField().getText())) {
            timeConvertForm.getTimestampTextField().setText(String.valueOf(System.currentTimeMillis()));
        }
        if ("".equals(timeConvertForm.getGmtTextField().getText())) {
            timeConvertForm.getGmtTextField().setText(DateFormatUtils.format(new Date(), TIME_FORMAT));
        }

        timeConvertForm.getTimeConvertPanel().updateUI();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        timeConvertPanel = new JPanel();
        timeConvertPanel.setLayout(new GridLayoutManager(3, 5, new Insets(50, 50, 0, 50), -1, -1));
        final Spacer spacer1 = new Spacer();
        timeConvertPanel.add(spacer1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 100, 0), -1, -1));
        timeConvertPanel.add(panel1, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 20, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        currentGmtLabel = new JLabel();
        Font currentGmtLabelFont = this.$$$getFont$$$(null, -1, 36, currentGmtLabel.getFont());
        if (currentGmtLabelFont != null) currentGmtLabel.setFont(currentGmtLabelFont);
        currentGmtLabel.setForeground(new Color(-14739));
        currentGmtLabel.setText("--");
        panel2.add(currentGmtLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        copyCurrentGmtButton = new JButton();
        copyCurrentGmtButton.setText("复制");
        panel2.add(copyCurrentGmtButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel2.add(spacer2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        currentTimestampLabel = new JLabel();
        Font currentTimestampLabelFont = this.$$$getFont$$$(null, -1, 36, currentTimestampLabel.getFont());
        if (currentTimestampLabelFont != null) currentTimestampLabel.setFont(currentTimestampLabelFont);
        currentTimestampLabel.setForeground(new Color(-14739));
        currentTimestampLabel.setText("--");
        panel3.add(currentTimestampLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        copyCurrentTimestampButton = new JButton();
        copyCurrentTimestampButton.setText("复制");
        panel3.add(copyCurrentTimestampButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel3.add(spacer3, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 30, 420), -1, -1));
        timeConvertPanel.add(panel4, new GridConstraints(1, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setHorizontalAlignment(10);
        label1.setHorizontalTextPosition(11);
        label1.setText("时间戳(Unix timestamp)");
        panel4.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        timestampTextField = new JTextField();
        Font timestampTextFieldFont = this.$$$getFont$$$(null, -1, 26, timestampTextField.getFont());
        if (timestampTextFieldFont != null) timestampTextField.setFont(timestampTextFieldFont);
        panel4.add(timestampTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        copyGeneratedTimestampButton = new JButton();
        copyGeneratedTimestampButton.setText("复制");
        panel4.add(copyGeneratedTimestampButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setHorizontalAlignment(10);
        label2.setHorizontalTextPosition(11);
        label2.setText("本地时间(GMT +08)");
        panel4.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gmtTextField = new JTextField();
        Font gmtTextFieldFont = this.$$$getFont$$$(null, -1, 26, gmtTextField.getFont());
        if (gmtTextFieldFont != null) gmtTextField.setFont(gmtTextFieldFont);
        panel4.add(gmtTextField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        copyGeneratedLocalTimeButton = new JButton();
        copyGeneratedLocalTimeButton.setText("复制");
        panel4.add(copyGeneratedLocalTimeButton, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 4, new Insets(10, 0, 10, 0), -1, -1));
        panel4.add(panel5, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        toLocalTimeButton = new JButton();
        toLocalTimeButton.setIcon(new ImageIcon(getClass().getResource("/icon/arrow-down.png")));
        toLocalTimeButton.setText("转换");
        toLocalTimeButton.setToolTipText("时间戳转换为本地时间");
        panel5.add(toLocalTimeButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        toTimestampButton = new JButton();
        toTimestampButton.setIcon(new ImageIcon(getClass().getResource("/icon/arrow-up.png")));
        toTimestampButton.setText("转换");
        toTimestampButton.setToolTipText("本地时间转换为时间戳");
        panel5.add(toTimestampButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        unitComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("秒(s)");
        defaultComboBoxModel1.addElement("毫秒(ms)");
        unitComboBox.setModel(defaultComboBoxModel1);
        panel5.add(unitComboBox, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel5.add(spacer4, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return timeConvertPanel;
    }

}
