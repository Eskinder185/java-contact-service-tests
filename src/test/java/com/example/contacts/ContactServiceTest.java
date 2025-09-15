package com.example.contacts;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ContactServiceTest {
    private ContactService contactService;
    private Contact contact;

    @BeforeEach
    public void setUp() {
        contactService = new ContactService();
        contact = new Contact("123", "John", "Doe", "1234567890", "123 Main St");
    }

    @Test
    public void testAddContact() {
        contactService.addContact(contact);
        Contact retrieved = contactService.getContact("123");
        assertNotNull(retrieved);
        assertEquals("John", retrieved.getFirstName());
        assertEquals("Doe", retrieved.getLastName());
    }

    @Test
    public void testAddDuplicateContact() {
        contactService.addContact(contact);
        
        Contact duplicate = new Contact("123", "Jane", "Smith", "0987654321", "456 Oak Ave");
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.addContact(duplicate);
        });
    }

    @Test
    public void testDeleteContact() {
        contactService.addContact(contact);
        assertNotNull(contactService.getContact("123"));
        
        contactService.deleteContact("123");
        assertNull(contactService.getContact("123"));
    }

    @Test
    public void testDeleteNonExistentContact() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.deleteContact("999");
        });
    }

    @Test
    public void testUpdateContact() {
        contactService.addContact(contact);
        
        contactService.updateContact("123", "Jane", "Smith", "0987654321", "456 Oak Ave");
        
        Contact updated = contactService.getContact("123");
        assertEquals("Jane", updated.getFirstName());
        assertEquals("Smith", updated.getLastName());
        assertEquals("0987654321", updated.getPhone());
        assertEquals("456 Oak Ave", updated.getAddress());
    }

    @Test
    public void testUpdateNonExistentContact() {
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateContact("999", "Jane", "Smith", "0987654321", "456 Oak Ave");
        });
    }

    @Test
    public void testGetContact() {
        assertNull(contactService.getContact("123"));
        
        contactService.addContact(contact);
        Contact retrieved = contactService.getContact("123");
        assertNotNull(retrieved);
        assertEquals(contact, retrieved);
    }

    @Test
    public void testUpdateContactValidation() {
        contactService.addContact(contact);
        
        // Test that validation is enforced during updates
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateContact("123", null, "Smith", "0987654321", "456 Oak Ave");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateContact("123", "Jane", null, "0987654321", "456 Oak Ave");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateContact("123", "Jane", "Smith", null, "456 Oak Ave");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            contactService.updateContact("123", "Jane", "Smith", "0987654321", null);
        });
    }

    @Test
    public void testMultipleContacts() {
        Contact contact1 = new Contact("123", "John", "Doe", "1234567890", "123 Main St");
        Contact contact2 = new Contact("456", "Jane", "Smith", "0987654321", "456 Oak Ave");
        
        contactService.addContact(contact1);
        contactService.addContact(contact2);
        
        assertEquals("John", contactService.getContact("123").getFirstName());
        assertEquals("Jane", contactService.getContact("456").getFirstName());
        
        contactService.deleteContact("123");
        assertNull(contactService.getContact("123"));
        assertNotNull(contactService.getContact("456"));
    }
}
