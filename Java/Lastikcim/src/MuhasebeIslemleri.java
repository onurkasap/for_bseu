
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MuhasebeIslemleri {

    private JFrame parentFrame;
    private JTable muhasebeTablo;
    private JDateChooser muhasebeTarihSecici;

    public MuhasebeIslemleri(JFrame parentFrame, JTable muhasebeTablo, JDateChooser muhasebeTarihSecici) {
        this.parentFrame = parentFrame;
        this.muhasebeTablo = muhasebeTablo;
        this.muhasebeTarihSecici = muhasebeTarihSecici;
    }

    public void tabloyuBuguneGoreYenile() {
        java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
        tabloyuTariheGoreYenile(today);
    }

    public void tabloyuTariheGoreYenile(java.sql.Date date) {
        try {
            Connection conn = TestConnection.getConnection();
            String query = "SELECT id, tarih, tur, miktar, isiYapan, aciklama FROM gelirGider WHERE tarih = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDate(1, date);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) muhasebeTablo.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getDate("tarih"),
                    rs.getString("tur"),
                    rs.getDouble("miktar"),
                    rs.getString("isiYapan"),
                    rs.getString("aciklama")
                });
            }
            hideIdColumn();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentFrame, "Veritabanı hatası!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void tabloyuAylikYenile() {
        java.util.Date selectedDate = muhasebeTarihSecici.getDate();
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(parentFrame, "Lütfen bir tarih seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(selectedDate);
        int yil = cal.get(Calendar.YEAR);
        int ay = cal.get(Calendar.MONTH) + 1;
        try {
            Connection conn = TestConnection.getConnection();
            String query = "SELECT id, tarih, tur, miktar, isiYapan, aciklama FROM gelirGider WHERE YEAR(tarih) = ? AND MONTH(tarih) = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, yil);
            stmt.setInt(2, ay);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) muhasebeTablo.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getDate("tarih"),
                    rs.getString("tur"),
                    rs.getDouble("miktar"),
                    rs.getString("isiYapan"),
                    rs.getString("aciklama")
                });
            }
            hideIdColumn();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentFrame, "Veritabanı hatası!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void gelirGiderEklePopup(String tur) {
        JDialog dialog = new JDialog(parentFrame, (tur.equals("gelir") ? "Gelir Ekle" : "Gider Ekle"), true);
        dialog.setLayout(new GridLayout(4, 2, 10, 10));
        JLabel isiYapanLabel = new JLabel("İşi Yapan:");
        JTextField isiYapanField = new JTextField();
        JLabel miktarLabel = new JLabel("Tutar:");
        JTextField miktarField = new JTextField();
        JLabel aciklamaLabel = new JLabel("Açıklama:");
        JTextField aciklamaField = new JTextField();
        JButton kaydetButton = new JButton("Kaydet");
        JButton iptalButton = new JButton("İptal");

        dialog.add(isiYapanLabel);
        dialog.add(isiYapanField);
        dialog.add(miktarLabel);
        dialog.add(miktarField);
        dialog.add(aciklamaLabel);
        dialog.add(aciklamaField);
        dialog.add(kaydetButton);
        dialog.add(iptalButton);

        kaydetButton.addActionListener(e -> {
            String isiYapan = isiYapanField.getText().trim();
            String miktarStr = miktarField.getText().trim();
            String aciklama = aciklamaField.getText().trim();

            if (isiYapan.isEmpty() || miktarStr.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "İşi Yapan ve Tutar alanları zorunlu.", "Uyarı", JOptionPane.WARNING_MESSAGE);
                return;
            }
            double miktar;
            try {
                miktar = Double.parseDouble(miktarStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Tutar sayısal olmalı.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Connection conn = TestConnection.getConnection();
                java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
                String insert = "INSERT INTO gelirGider (aciklama, miktar, tarih, isiYapan, tur) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(insert);
                stmt.setString(1, aciklama);
                stmt.setDouble(2, miktar);
                stmt.setDate(3, today);
                stmt.setString(4, isiYapan);
                stmt.setString(5, tur);
                stmt.executeUpdate();

                dialog.dispose();
                tabloyuBuguneGoreYenile();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Veritabanı hatası oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        iptalButton.addActionListener(e -> dialog.dispose());
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }

    public void kaydiSil() {
        int selectedRow = muhasebeTablo.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(parentFrame, "Lütfen silmek için bir satır seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Object idObj = muhasebeTablo.getValueAt(selectedRow, 0);
        int id;
        try {
            id = Integer.parseInt(idObj.toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parentFrame, "ID değeri alınamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(parentFrame, "Seçili kaydı silmek istediğinize emin misiniz?", "Onay", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            Connection conn = TestConnection.getConnection();
            String sql = "DELETE FROM gelirGider WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(parentFrame, "Kayıt başarıyla silindi.");
            tabloyuBuguneGoreYenile();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(parentFrame, "Veritabanı hatası oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hideIdColumn() {
        if (muhasebeTablo.getColumnCount() > 0) {
            muhasebeTablo.getColumnModel().getColumn(0).setMinWidth(0);
            muhasebeTablo.getColumnModel().getColumn(0).setMaxWidth(0);
            muhasebeTablo.getColumnModel().getColumn(0).setWidth(0);
        }
    }

    public double getBugunkuToplamGelir() {
        double toplamGelir = 0.0;
        try {
            Connection conn = TestConnection.getConnection();
            String sql = "SELECT SUM(miktar) as toplamGelir FROM gelirGider WHERE tarih = CURDATE() AND tur = 'gelir'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                toplamGelir = rs.getDouble("toplamGelir");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toplamGelir;
    }
    
    
        // GÜNLÜK TOPLAM GELİR/GİDER
    public void gunlukToplamGoster() {
        try {
            Connection conn = TestConnection.getConnection();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String today = sdf.format(new java.util.Date());

            String query = "SELECT tur, SUM(miktar) as toplam FROM gelirGider WHERE tarih = ? GROUP BY tur";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, today);
            ResultSet rs = stmt.executeQuery();

            double toplamGelir = 0, toplamGider = 0;
            while (rs.next()) {
                if ("gelir".equalsIgnoreCase(rs.getString("tur"))) {
                    toplamGelir = rs.getDouble("toplam");
                } else if ("gider".equalsIgnoreCase(rs.getString("tur"))) {
                    toplamGider = rs.getDouble("toplam");
                }
            }

            JOptionPane.showMessageDialog(parentFrame,
                    "Bugünün Toplam Geliri: " + toplamGelir + " TL\nBugünün Toplam Gideri: " + toplamGider + " TL",
                    "Günlük Toplamlar",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentFrame, "Veritabanı hatası oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    // AYLIK TOPLAM GELİR/GİDER
    public void aylikToplamGoster() {
        try {
            Connection conn = TestConnection.getConnection();

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1; // Calendar.MONTH 0'dan başlar!

            // YYYY-MM şeklinde filtrele
            String ayBaslangic = String.format("%04d-%02d-01", year, month);
            String aySonu = String.format("%04d-%02d-31", year, month);

            String query = "SELECT tur, SUM(miktar) as toplam FROM gelirGider WHERE tarih >= ? AND tarih <= ? GROUP BY tur";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, ayBaslangic);
            stmt.setString(2, aySonu);
            ResultSet rs = stmt.executeQuery();

            double toplamGelir = 0, toplamGider = 0;
            while (rs.next()) {
                if ("gelir".equalsIgnoreCase(rs.getString("tur"))) {
                    toplamGelir = rs.getDouble("toplam");
                } else if ("gider".equalsIgnoreCase(rs.getString("tur"))) {
                    toplamGider = rs.getDouble("toplam");
                }
            }

            JOptionPane.showMessageDialog(parentFrame,
                    "Bu Ayın Toplam Geliri: " + toplamGelir + " TL\nBu Ayın Toplam Gideri: " + toplamGider + " TL",
                    "Aylık Toplamlar",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentFrame, "Veritabanı hatası oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
}
