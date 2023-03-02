package com.jca.thedoor.util;

import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ArrayUtilTest {

    //<editor-fold desc="isArrayNullOrEmpty">
    @Order(1)
    @DisplayName("1. isArrayNullOrEmpty. When array is null must return true")
    @Test
    public void isArrayNullOrEmptyWhenArrayIsNullMustReturnTrue() {
        Assert.assertTrue(ArrayUtil.isArrayNullOrEmpty(null));
    }

    @Order(2)
    @DisplayName("2. isArrayNullOrEmpty. When array is empty must return true")
    @Test
    public void isArrayNullOrEmptyWhenArrayIsEmptyMustReturnTrue() {
        Assert.assertTrue(ArrayUtil.isArrayNullOrEmpty(new String[]{}));
    }

    @Order(3)
    @DisplayName("3. isArrayNullOrEmpty. When array is not null and not empty must return false")
    @Test
    public void isArrayNullOrEmptyWhenArrayIsNotNullAndNotEMptyMustReturnFalse() {
        String[] arr = {"one", "two"};
        Assert.assertFalse(ArrayUtil.isArrayNullOrEmpty(arr));
    }

    @Order(4)
    @DisplayName("4. isArrayNullOrEmpty. When array is not null and first value empty must return false")
    @Test
    public void isArrayNullOrEmptyWhenArrayIsNotNullAndEMptyMustReturnFalse() {
        String[] arr = {""};
        Assert.assertFalse(ArrayUtil.isArrayNullOrEmpty(arr));
    }

    @Order(5)
    @DisplayName("5. isArrayNullOrEmpty. When array first value empty and next is not empty must " +
                 "return false")
    @Test
    public void isArrayNullOrEmptyWhenFirstValueEmptyAndNextNotEmptyMustReturnFalse() {
        String[] arr = {"", "two"};
        Assert.assertFalse(ArrayUtil.isArrayNullOrEmpty(arr));
    }
    //</editor-fold>

    //<editor-fold desc="isListStringNullOrEmpty">
    @Order(6)
    @DisplayName("1. isListStringNullOrEmpty. When list is null must return true")
    @Test
    public void isListStringNullOrEmptyWhenListIsNullMustReturnTrue() {
        Assert.assertTrue(ArrayUtil.isListStringNullOrEmpty(null));
    }

    @Order(7)
    @DisplayName("2. isListStringNullOrEmpty. When list is empty must return true")
    @Test
    public void isListStringNullOrEmptyWhenListIsEmptyMustReturnTrue() {
        Assert.assertTrue(ArrayUtil.isListStringNullOrEmpty(new LinkedList<>()));
    }

    @Order(8)
    @DisplayName("3. isListStringNullOrEmpty. When list is not null and not empty must return false")
    @Test
    public void isListStringNullOrEmptyWhenArrayIsNotNullAndNotEMptyMustReturnFalse() {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        Assert.assertFalse(ArrayUtil.isListStringNullOrEmpty(list));
    }

    @Order(9)
    @DisplayName("4. isArrayNullOrEmpty. When array is not null and first value empty must return true")
    @Test
    public void isListStringNullOrEmptyWhenListIsNotNullAndEmptyMustReturnTrue() {
        List<String> list = new ArrayList<>();
        list.add("");
        Assert.assertTrue(ArrayUtil.isListStringNullOrEmpty(list));
    }

    @Order(10)
    @DisplayName("5. isListStringNullOrEmpty. When list first value empty and next is not empty must " +
            "return false")
    @Test
    public void isListStringNullOrEmptyWhenFirstValueEmptyAndNextNotEmptyMustReturnTrue() {
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("two");
        Assert.assertTrue(ArrayUtil.isListStringNullOrEmpty(list));
    }
    //</editor-fold>
}
