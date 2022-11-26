package com.jca.thedoor.util;

import org.junit.Assert;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ArrayUtilTest {

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
}
