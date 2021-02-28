package gusakova;

import java.sql.*;
import java.util.Scanner;

public class TestDB {
    public static void main(String[] args) {

        Connection c;
        Statement stmt;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://127.0.0.1:5432/Test", "postgres", "1234");

            c.setAutoCommit(false);
            System.out.println("-- Opened database successfully");
            String sql;

            //-------------- CREATE TABLE ---------------
            stmt = c.createStatement();
            sql = "CREATE TABLE public.\"Priority\"\n" +
                    "(\n" +
                    "    \"Id\" integer NOT NULL,\n" +
                    "    \"Priority\" text COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                    "    CONSTRAINT \"Priority_pkey\" PRIMARY KEY (\"Id\")\n" +
                    ")\n" +
                    "\n" +
                    "TABLESPACE pg_default;\n" +
                    "\n" +
                    "ALTER TABLE public.\"Priority\"\n" +
                    "    OWNER to postgres;";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            System.out.println("-- Table created successfully");

            //--------------- INSERT ---------------

            Scan scan = new Scan();

            stmt = c.createStatement();
            sql = "INSERT INTO public.\"Order\"(\n" +
                    "\t\"Id\", \"Order_date\", \"Client_Id\", \"Manager_Id\", \"Contractor_Id\", \"Priority\", \"Description\", \"Status\")\n" +
                    "\tVALUES (" +
                    scan.val.getId() + "," + "'" + scan.val.getOrder_date() + "'" + "," + scan.val.getClient_Id() + "," + scan.val.getManager_Id() + "," + scan.val.getContractor_Id() + ","
                    + "'" + scan.val.getPriority() + "'" + "," + "'" + scan.val.getDescription() + "'" + "," + scan.val.getStatus() + ");";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            System.out.println("-- Records created successfully");


            //--------------- SELECT------------------
            Integer id;
            String order_date;
            Integer client_Id;
            Integer manager_Id;
            Integer contractor_Id;
            String priority;
            String description;
            int status;
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM \"Order\"" +
                    "WHERE \"Order_date\" = '25.02.2021';");
            while (rs.next()) {
                id = rs.getInt("Id");
                order_date = rs.getString("Order_date");
                client_Id = rs.getInt("Client_Id");
                manager_Id = rs.getInt("Manager_Id");
                contractor_Id = rs.getInt("Contractor_Id");
                priority = rs.getString("Priority");
                description = rs.getString("Description");
                status = rs.getInt("Status");
                System.out.println(String.format("ID=%s OrderDate=%s ClientId=%s ManagerId=%s ContractorId=%s Priority=%s " +
                        "Description=%s Status=%s ", id, order_date, client_Id, manager_Id, contractor_Id, priority, description, status));
            }
            rs = stmt.executeQuery("SELECT MIN(\"Order_date\") FROM public.\"Order\"");
            if (rs.next()) {
                String min = rs.getString(1);
                System.out.println(min);
            }
            rs.close();
            stmt.close();
            c.commit();
            System.out.println("-- Operation SELECT done successfully");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}

class Value {

    Integer id;
    String order_date;
    Integer client_Id;
    Integer manager_Id;
    Integer contractor_Id;
    String priority;
    String description;
    int status;

    public Value(Integer id, String order_date,
                 Integer client_Id, Integer manager_Id, Integer contractor_Id,
                 String priority, String description, int status) {
        this.id = id;
        this.order_date = order_date;
        this.client_Id = client_Id;
        this.manager_Id = manager_Id;
        this.contractor_Id = contractor_Id;
        this.priority = priority;
        this.description = description;
        this.status = status;

    }

    public Integer getId() {
        return id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public Integer getClient_Id() {
        return client_Id;
    }

    public Integer getManager_Id() {
        return manager_Id;
    }

    public Integer getContractor_Id() {
        return contractor_Id;
    }

    public String getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public int getStatus() {
        return status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public void setClient_Id(Integer client_Id) {
        this.client_Id = client_Id;
    }

    public void setManager_Id(Integer manager_Id) {
        this.manager_Id = manager_Id;
    }

    public void setContractor_Id(Integer contractor_Id) {
        this.contractor_Id = contractor_Id;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}

class Scan {
    Scanner sc = new Scanner(System.in);
    Integer order = sc.nextInt();
    String date = sc.next();
    Integer client = sc.nextInt();
    Integer manager = sc.nextInt();
    Integer contractor = sc.nextInt();
    String prior = sc.next();
    String desc = sc.next();
    Integer st = sc.nextInt();
    Value val = new Value(order, date, client, manager, contractor, prior, desc, st);

}

