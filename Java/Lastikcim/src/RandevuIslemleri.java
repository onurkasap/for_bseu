
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RandevuIslemleri {

    private JFrame parentFrame;
    private JTable randevuTablo;
    private String isletmeUsername;

    public RandevuIslemleri(JFrame parentFrame, JTable randevuTablo, String isletmeUsername) {
        this.parentFrame = parentFrame;
        this.randevuTablo = randevuTablo;
        this.isletmeUsername = isletmeUsername;
    }

    public void randevuTabloYenile() {
        try {
            Connection conn = TestConnection.getConnection();
            String query = "SELECT tarih, saat, adSoyad, plaka, markaModel, genislik, yukseklik, jant, durum "
                    + "FROM randevular WHERE isletmeUsername = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, isletmeUsername);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) randevuTablo.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getDate("tarih"),
                    rs.getString("saat"),
                    rs.getString("adSoyad"),
                    rs.getString("plaka"),
                    rs.getString("markaModel"),
                    rs.getString("genislik"),
                    rs.getString("yukseklik"),
                    rs.getString("jant"),
                    rs.getString("durum")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentFrame, "Veritabanı sorgusu sırasında bir hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void randevuTabloTarihleYenile(java.sql.Date sqlDate) {
        try {
            Connection conn = TestConnection.getConnection();
            String query = "SELECT tarih, saat, adSoyad, plaka, markaModel, genislik, yukseklik, jant, durum "
                    + "FROM randevular WHERE isletmeUsername = ? AND tarih = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, isletmeUsername);
            stmt.setDate(2, sqlDate);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) randevuTablo.getModel();
            model.setRowCount(0);
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                model.addRow(new Object[]{
                    rs.getDate("tarih"),
                    rs.getString("saat"),
                    rs.getString("adSoyad"),
                    rs.getString("plaka"),
                    rs.getString("markaModel"),
                    rs.getString("genislik"),
                    rs.getString("yukseklik"),
                    rs.getString("jant"),
                    rs.getString("durum")
                });
            }
            if (!hasData) {
                JOptionPane.showMessageDialog(parentFrame, "Seçilen tarihte randevu bulunamadı.", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentFrame, "Veritabanı sorgusu sırasında bir hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void randevuIslemYap(JFrame parentFrame, MuhasebeIslemleri muhasebeIslem) {
        int selectedRow = randevuTablo.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(parentFrame, "Lütfen tablodan bir randevu seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String plaka = (String) randevuTablo.getValueAt(selectedRow, 3);
        String saat = (String) randevuTablo.getValueAt(selectedRow, 1);
        java.sql.Date tarih = (java.sql.Date) randevuTablo.getValueAt(selectedRow, 0);

        JDialog dialog = new JDialog(parentFrame, "İşlem Yap", true);
        dialog.setLayout(new GridLayout(4, 2, 10, 10));
        JLabel isiYapanLabel = new JLabel("İşi Yapan:");
        JTextField isiYapanField = new JTextField();
        JLabel ucretLabel = new JLabel("Ücret:");
        JTextField ucretField = new JTextField();
        JLabel aciklamaLabel = new JLabel("Açıklama:");
        JTextField aciklamaField = new JTextField();
        JButton uygulaButton = new JButton("Uygula");
        JButton iptalButton = new JButton("İşlemi İptal Et");

        dialog.add(isiYapanLabel);
        dialog.add(isiYapanField);
        dialog.add(ucretLabel);
        dialog.add(ucretField);
        dialog.add(aciklamaLabel);
        dialog.add(aciklamaField);
        dialog.add(uygulaButton);
        dialog.add(iptalButton);

        uygulaButton.addActionListener(e -> {
            String isiYapan = isiYapanField.getText().trim();
            String ucretStr = ucretField.getText().trim();
            String aciklama = aciklamaField.getText().trim();
            if (isiYapan.isEmpty() || ucretStr.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "İşi Yapan ve Ücret alanları zorunlu.", "Uyarı", JOptionPane.WARNING_MESSAGE);
                return;
            }
            double ucret;
            try {
                ucret = Double.parseDouble(ucretStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Ücret sayısal olmalı.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Connection conn = TestConnection.getConnection();
                String update = "UPDATE randevular SET durum = ? WHERE plaka = ? AND tarih = ? AND saat = ?";
                PreparedStatement stmt = conn.prepareStatement(update);
                stmt.setString(1, "Yapıldı");
                stmt.setString(2, plaka);
                stmt.setDate(3, tarih);
                stmt.setString(4, saat);
                stmt.executeUpdate();

                // Gelir/Gider tablosuna ekle
                String insert = "INSERT INTO gelirGider (aciklama, miktar, tarih, isiYapan, tur) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt2 = conn.prepareStatement(insert);
                stmt2.setString(1, aciklama);
                stmt2.setDouble(2, ucret);
                stmt2.setDate(3, tarih);
                stmt2.setString(4, isiYapan);
                stmt2.setString(5, "gelir");
                stmt2.executeUpdate();

                dialog.dispose();
                randevuTabloYenile(); // Tabloyu yenile
                if (muhasebeIslem != null) {
                    muhasebeIslem.tabloyuBuguneGoreYenile(); // Muhasebe tablosunu da yenile
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Veritabanı hatası oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        iptalButton.addActionListener(e -> {
            try {
                Connection conn = TestConnection.getConnection();
                String update = "UPDATE randevular SET durum = ? WHERE plaka = ? AND tarih = ? AND saat = ?";
                PreparedStatement stmt = conn.prepareStatement(update);
                stmt.setString(1, "İptal Edildi");
                stmt.setString(2, plaka);
                stmt.setDate(3, tarih);
                stmt.setString(4, saat);
                stmt.executeUpdate();

                dialog.dispose();
                randevuTabloYenile();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Veritabanı hatası oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }

    public List<Object[]> getYaklasanRandevular(int limit) {
        List<Object[]> randevular = new ArrayList<>();
        try {
            Connection conn = TestConnection.getConnection();
            String sql = "SELECT tarih, saat, adSoyad, plaka FROM randevular WHERE tarih >= CURDATE() AND durum != 'Yapıldı' ORDER BY tarih, saat LIMIT ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Object[] row = {
                    rs.getDate("tarih"),
                    rs.getString("saat"),
                    rs.getString("adSoyad"),
                    rs.getString("plaka")
                };
                randevular.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return randevular;
    }
    
    public void isletmeRandevuSil() {
        int selectedRow = randevuTablo.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(parentFrame, "Lütfen silmek için bir randevu seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Tablo modelindeki bilgileri al
        java.sql.Date tarih = (java.sql.Date) randevuTablo.getValueAt(selectedRow, 0);
        String saat = (String) randevuTablo.getValueAt(selectedRow, 1);
        String plaka = (String) randevuTablo.getValueAt(selectedRow, 3);

        int confirm = JOptionPane.showConfirmDialog(
                parentFrame,
                "Seçili randevuyu silmek istediğinize emin misiniz?",
                "Randevu Silme Onayı",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            Connection conn = TestConnection.getConnection();
            String sql = "DELETE FROM randevular WHERE isletmeUsername = ? AND tarih = ? AND saat = ? AND plaka = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, isletmeUsername);
            stmt.setDate(2, tarih);
            stmt.setString(3, saat);
            stmt.setString(4, plaka);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(parentFrame, "Randevu başarıyla silindi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                randevuTabloYenile();
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Randevu silinemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentFrame, "Veritabanı hatası oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
}
