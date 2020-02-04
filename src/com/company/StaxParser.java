package com.company;

import com.company.models.Employee;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StaxParser {
    public static void main(String[] args) throws FileNotFoundException, XMLStreamException
    {
        File file = new File("resources//employees.xml");
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(file));
        List<Employee> employeeList = new ArrayList<>();

        Employee employee = null;

        while (eventReader.hasNext())
        {
            XMLEvent xmlEvent = eventReader.nextEvent();

            if (xmlEvent.isStartElement())
            {
                StartElement startElement = xmlEvent.asStartElement();

                if("employee".equalsIgnoreCase(startElement.getName().getLocalPart())) {
                    employee = new Employee();
                }

                @SuppressWarnings("unchecked")
                Iterator<Attribute> iterator = startElement.getAttributes();

                while (iterator.hasNext())
                {
                    Attribute attribute = iterator.next();
                    QName name = attribute.getName();
                    if("id".equalsIgnoreCase(name.getLocalPart())) {
                        employee.setId(Integer.valueOf(attribute.getValue()));
                    }
                }

                switch (startElement.getName().getLocalPart())
                {
                    case "firstName":
                        Characters firstNameDataEvent = (Characters) eventReader.nextEvent();
                        employee.setFirstName(firstNameDataEvent.getData());
                        break;

                    case "lastName":
                        Characters lastNameDataEvent = (Characters) eventReader.nextEvent();
                        employee.setLastName(lastNameDataEvent.getData());
                        break;

                    case "location":
                        Characters locationDataEvent = (Characters) eventReader.nextEvent();
                        employee.setLocation(locationDataEvent.getData());
                        break;
                }
            }

            if (xmlEvent.isEndElement())
            {
                EndElement endElement = xmlEvent.asEndElement();

                if("employee".equalsIgnoreCase(endElement.getName().getLocalPart())) {
                    employeeList.add(employee);
                }
            }
        }

        System.out.println(employeeList);

    }
}
