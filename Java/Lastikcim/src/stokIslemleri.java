
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class stokIslemleri {

    private JTable stokTablo;
    private JFrame parentFrame;

    public stokIslemleri(JFrame parentFrame, JTable stokTablo) {
        this.parentFrame = parentFrame;
        this.stokTablo = stokTablo;
    }

    public void stokTabloYenile() {
        try {
            Connection conn = TestConnection.getConnection();
            String query = "SELECT id, urunIsmi, urunMiktari, urunFiyati FROM stokTakip";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) stokTablo.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("urunIsmi"),
                    rs.getInt("urunMiktari"),
                    rs.getDouble("urunFiyati")
                });
            }
            // ID sütununu gizle
            stokTablo.getColumnModel().getColumn(0).setMinWidth(0);
            stokTablo.getColumnModel().getColumn(0).setMaxWidth(0);
            stokTablo.getColumnModel().getColumn(0).setWidth(0);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parentFrame, "Veritabanı hatası!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void urunEkle() {
        JDialog dialog = new JDialog(parentFrame, "Ürün Ekle", true);
        dialog.setLayout(new GridLayout(4, 2, 10, 10));
        JLabel isimLabel = new JLabel("Ürün İsmi:");
        JTextField isimField = new JTextField();
        JLabel miktarLabel = new JLabel("Ürün Miktarı:");
        JTextField miktarField = new JTextField();
        JLabel fiyatLabel = new JLabel("Ürün Fiyatı (Tane):");
        JTextField fiyatField = new JTextField();
        JButton ekleButton = new JButton("Ekle");
        JButton iptalButton = new JButton("İptal");

        dialog.add(isimLabel);
        dialog.add(isimField);
        dialog.add(miktarLabel);
        dialog.add(miktarField);
        dialog.add(fiyatLabel);
        dialog.add(fiyatField);
        dialog.add(ekleButton);
        dialog.add(iptalButton);

        ekleButton.addActionListener(e -> {
            String isim = isimField.getText().trim();
            String miktarStr = miktarField.getText().trim();
            String fiyatStr = fiyatField.getText().trim();

            if (isim.isEmpty() || miktarStr.isEmpty() || fiyatStr.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Tüm alanları doldurun!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int miktar;
            double fiyat;
            try {
                miktar = Integer.parseInt(miktarStr);
                fiyat = Double.parseDouble(fiyatStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Miktar ve fiyat sayısal olmalı!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Connection conn = TestConnection.getConnection();
                String sql = "INSERT INTO stokTakip (urunIsmi, urunMiktari, urunFiyati) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, isim);
                stmt.setInt(2, miktar);
                stmt.setDouble(3, fiyat);
                stmt.executeUpdate();
                dialog.dispose();
                stokTabloYenile();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Veritabanı hatası!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        iptalButton.addActionListener(e -> dialog.dispose());
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }

    public void urunCikar() {
        int selectedRow = stokTablo.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(parentFrame, "Lütfen bir ürün seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Object idObj = stokTablo.getValueAt(selectedRow, 0);
        int id = Integer.parseInt(idObj.toString());

        int confirm = JOptionPane.showConfirmDialog(parentFrame, "Ürünü silmek istiyor musunuz?", "Onay", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            Connection conn = TestConnection.getConnection();
            String sql = "DELETE FROM stokTakip WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stokTabloYenile();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(parentFrame, "Veritabanı hatası!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void urunMiktarGuncelle() {
        int selectedRow = stokTablo.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(parentFrame, "Lütfen bir ürün seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Object idObj = stokTablo.getValueAt(selectedRow, 0);
        int id = Integer.parseInt(idObj.toString());
        String mevcutMiktar = stokTablo.getValueAt(selectedRow, 2).toString();
        String mevcutFiyat = stokTablo.getValueAt(selectedRow, 3).toString();

        JDialog dialog = new JDialog(parentFrame, "Miktar/Fiyat Güncelle", true);
        dialog.setLayout(new GridLayout(3, 2, 10, 10));
        JLabel miktarLabel = new JLabel("Ürün Miktarı:");
        JTextField miktarField = new JTextField(mevcutMiktar);
        JLabel fiyatLabel = new JLabel("Ürün Fiyatı:(Tane)");
        JTextField fiyatField = new JTextField(mevcutFiyat);
        JButton guncelleButton = new JButton("Güncelle");
        JButton iptalButton = new JButton("İptal");

        dialog.add(miktarLabel);
        dialog.add(miktarField);
        dialog.add(fiyatLabel);
        dialog.add(fiyatField);
        dialog.add(guncelleButton);
        dialog.add(iptalButton);

        guncelleButton.addActionListener(e -> {
            String miktarStr = miktarField.getText().trim();
            String fiyatStr = fiyatField.getText().trim();
            if (miktarStr.isEmpty() || fiyatStr.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Tüm alanları doldurun!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int miktar;
            double fiyat;
            try {
                miktar = Integer.parseInt(miktarStr);
                fiyat = Double.parseDouble(fiyatStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Miktar ve fiyat sayısal olmalı!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Connection conn = TestConnection.getConnection();
                String sql = "UPDATE stokTakip SET urunMiktari = ?, urunFiyati = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, miktar);
                stmt.setDouble(2, fiyat);
                stmt.setInt(3, id);
                stmt.executeUpdate();
                dialog.dispose();
                stokTabloYenile();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Veritabanı hatası!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        iptalButton.addActionListener(e -> dialog.dispose());
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }
}
