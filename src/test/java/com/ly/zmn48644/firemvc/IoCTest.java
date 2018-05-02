package com.ly.zmn48644.firemvc;

import com.ly.zmn48644.demo.controller.IndexController;
import com.ly.zmn48644.firemvc.context.ApplicationContext;

public class IoCTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext("application.properties");

        IndexController indexController = (IndexController)applicationContext.getBean("indexController");

        indexController.home();
    }
}

[
        {
        "serialNumber": "QYCNYBI86W4XSU108CKFOTFH7",
        "expires": "8:45",
        "trainNumber": [
        "G1285",
        "G1537",
        "G1286",
        "G1288",
        "G2205",
        "G2212",
        "G1812",
        "G1818",
        "G370",
        "G368",
        "G1964",
        "G1962",
        "G1821",
        "G1810",
        "G1804",
        "G1826",
        "G1908",
        "G1906",
        "G1852",
        "G1854",
        "G1813",
        "G1936",
        "G1814",
        "G1914",
        "G2201",
        "G2208",
        "G2203",
        "G2210",
        "G1538"
        ]
        },
        {
        "serialNumber": "QYCNKZBPMSTGSU108ES70XX2F",
        "expires": "11:50",
        "trainNumber": [
        "G508",
        "G555",
        "D5932",
        "D5929",
        "G510",
        "G519",
        "G502",
        "G587",
        "G512",
        "G521",
        "G586",
        "G585",
        "G572",
        "G573",
        "G514",
        "G523",
        "G556",
        "G525",
        "G1143",
        "G1146",
        "G1147",
        "G516",
        "G513",
        "G527",
        "G1001",
        "G1032",
        "G1033",
        "G1031",
        "G1034",
        "G1022",
        "G1003",
        "G1012",
        "G1005",
        "G554",
        "G551",
        "G1014",
        "G1009",
        "G1036",
        "G1037",
        "G1148",
        "G1145",
        "G1136"
        ]
        }
        ]

