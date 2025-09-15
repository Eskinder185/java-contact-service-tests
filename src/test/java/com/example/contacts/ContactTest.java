package com.example.contacts;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContactTest {

    @Test
    public void testValidContactCreation() {
        Contact contact = new Contact("123", "John", "Doe", "1234567890", "123 Main St");
        
        assertEquals("123", contact.getContactId());
        assertEquals("John", contact.getFirstName());
        assertEquals("Doe", contact.getLastName());
        assertEquals("1234567890", contact.getPhone());
        assertEquals("123 Main St", contact.getAddress());
    }

    @Test
    public void testContactIdValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact(null, "John", "Doe", "1234567890", "123 Main St");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("", "John", "Doe", "1234567890", "123 Main St");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("   ", "John", "Doe", "1234567890", "123 Main St");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("12345678901", "John", "Doe", "1234567890", "123 Main St");
        });
    }

    @Test
    public void testFirstNameValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", null, "Doe", "1234567890", "123 Main St");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "", "Doe", "1234567890", "123 Main St");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "   ", "Doe", "1234567890", "123 Main St");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "JohnJohnJohn", "Doe", "1234567890", "123 Main St");
        });
    }

    @Test
    public void testLastNameValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", null, "1234567890", "123 Main St");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", "", "1234567890", "123 Main St");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", "   ", "1234567890", "123 Main St");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", "DoeDoeDoeDoe", "1234567890", "123 Main St");
        });
    }

    @Test
    public void testPhoneValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", "Doe", null, "123 Main St");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", "Doe", "123456789", "123 Main St");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", "Doe", "12345678901", "123 Main St");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", "Doe", "123456789a", "123 Main St");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", "Doe", "123-456-7890", "123 Main St");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", "Doe", "123 456 7890", "123 Main St");
        });
    }

    @Test
    public void testAddressValidation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", "Doe", "1234567890", null);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", "Doe", "1234567890", "");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", "Doe", "1234567890", "   ");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Contact("123", "John", "Doe", "1234567890", "1234567890123456789012345678901");
        });
    }

    @Test
    public void testSetters() {
        Contact contact = new Contact("123", "John", "Doe", "1234567890", "123 Main St");
        
        contact.setFirstName("Jane");
        assertEquals("Jane", contact.getFirstName());
        
        contact.setLastName("Smith");
        assertEquals("Smith", contact.getLastName());
        
        contact.setPhone("0987654321");
        assertEquals("0987654321", contact.getPhone());
        
        contact.setAddress("456 Oak Ave");
        assertEquals("456 Oak Ave", contact.getAddress());
    }

    @Test
    public void testSetterValidation() {
        Contact contact = new Contact("123", "John", "Doe", "1234567890", "123 Main St");
        
        assertThrows(IllegalArgumentException.class, () -> contact.setFirstName(null));
        assertThrows(IllegalArgumentException.class, () -> contact.setFirstName(""));
        assertThrows(IllegalArgumentException.class, () -> contact.setFirstName("   "));
        assertThrows(IllegalArgumentException.class, () -> contact.setFirstName("JohnJohnJohn"));
        
        assertThrows(IllegalArgumentException.class, () -> contact.setLastName(null));
        assertThrows(IllegalArgumentException.class, () -> contact.setLastName(""));
        assertThrows(IllegalArgumentException.class, () -> contact.setLastName("   "));
        assertThrows(IllegalArgumentException.class, () -> contact.setLastName("DoeDoeDoeDoe"));
        
        assertThrows(IllegalArgumentException.class, () -> contact.setPhone(null));
        assertThrows(IllegalArgumentException.class, () -> contact.setPhone("123456789"));
        assertThrows(IllegalArgumentException.class, () -> contact.setPhone("12345678901"));
        assertThrows(IllegalArgumentException.class, () -> contact.setPhone("123456789a"));
        assertThrows(IllegalArgumentException.class, () -> contact.setPhone("123-456-7890"));
        assertThrows(IllegalArgumentException.class, () -> contact.setPhone("123 456 7890"));
        
        assertThrows(IllegalArgumentException.class, () -> contact.setAddress(null));
        assertThrows(IllegalArgumentException.class, () -> contact.setAddress(""));
        assertThrows(IllegalArgumentException.class, () -> contact.setAddress("   "));
        assertThrows(IllegalArgumentException.class, () -> contact.setAddress("1234567890123456789012345678901"));
    }

    @Test
    public void testContactIdImmutable() {
        Contact contact = new Contact("123", "John", "Doe", "1234567890", "123 Main St");
        assertEquals("123", contact.getContactId());
        // Contact ID should be immutable - no setter exists
    }
}
