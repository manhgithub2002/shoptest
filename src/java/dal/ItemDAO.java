/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Category;
import model.Item;

/**
 *
 * @author lap
 */
public class ItemDAO extends DBContext {

    public ArrayList<Item> getAll() {
        String sql = "select * from item";
        ArrayList<Item> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            CategoryDAO cdb = new CategoryDAO();
            while (rs.next()) {
                Item x = new Item();
                x.setId(rs.getString("ID"));
                x.setName(rs.getString("name"));
                x.setStock(rs.getInt("stock"));
                x.setCost(rs.getDouble("costPrice"));
                x.setSell(rs.getDouble("sellPrice"));
                x.setImage(rs.getString("image"));
                x.setDescription(rs.getString("description"));
                Category c = cdb.get(rs.getString("categoryID"));
                x.setCategory(c);
                list.add(x);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public Item getItemByID(String id) {
        String sql = "select * from item where ID = ?";
        CategoryDAO cdb = new CategoryDAO();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Item x = new Item();
                x.setId(rs.getString("ID"));
                x.setName(rs.getString("name"));
                x.setStock(rs.getInt("stock"));
                x.setCost(rs.getDouble("costPrice"));
                x.setSell(rs.getDouble("sellPrice"));
                x.setImage(rs.getString("image"));
                x.setDescription(rs.getString("description"));
                Category c = cdb.get(rs.getString("categoryID"));
                x.setCategory(c);
                return x;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public ArrayList<Item> getListByPage(ArrayList<Item> list, int start, int end) {
        ArrayList<Item> res = new ArrayList<>();
        for (int i = start; i < end; i++) {
            res.add(list.get(i));
        }
        return res;
    }

    public ArrayList<Item> filterItems(String[] catID, Double from, Double to, String sortType) {
        String sql = "select * from item where 1 = 1 ";
        CategoryDAO cdb = new CategoryDAO();
        int size = cdb.getAll().size();
        if (catID != null) {
            if (catID.length < size) {
                sql += "and categoryID in (";
                for (int i = 0; i < catID.length; i++) {
                    sql += "'" + catID[i] + "'";
                    if (i != catID.length - 1) {
                        sql += ",";
                    } else {
                        sql += ")";
                    }
                }
            }
        }
        if (from != null) {
            sql += " and sellPrice > " + from;
        }
        if (to != null) {
            sql += " and sellPrice <= " + to;
        }
        switch (sortType) {
            case "1":
                sql += "";
                break;
            case "2":
                sql += " order by name";
                break;
            case "3":
                sql += " order by name desc";
                break;
            case "4":
                sql += " order by sellPrice";
                break;
            default:
                sql += " order by sellPrice desc";
        }
        ArrayList<Item> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Item x = new Item();
                x.setId(rs.getString("ID"));
                x.setName(rs.getString("name"));
                x.setStock(rs.getInt("stock"));
                x.setCost(rs.getDouble("costPrice"));
                x.setSell(rs.getDouble("sellPrice"));
                x.setImage(rs.getString("image"));
                x.setDescription(rs.getString("description"));
                Category c = cdb.get(rs.getString("categoryID"));
                x.setCategory(c);
                list.add(x);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public ArrayList<Item> getItemsByPrice(String[] catID, String op, String sortType) {
        Double pr1, pr2;
        switch (op) {
            case "1":
                pr1 = (double) 0;
                pr2 = (double) 500;
                break;
            case "2":
                pr1 = (double) 500;
                pr2 = (double) 1000;
                break;
            case "3":
                pr1 = (double) 1000;
                pr2 = (double) 1500;
                break;
            case "4":
                pr1 = (double) 1500;
                pr2 = null;
                break;
            default:
                pr1 = null;
                pr2 = null;
        }
        return filterItems(catID, pr1, pr2, sortType);
    }
    
    public void updateQuantity(Item it, int num){
        String sql = "update item set stock = stock + ? where ID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, num);
            st.setString(2, it.getId());
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
