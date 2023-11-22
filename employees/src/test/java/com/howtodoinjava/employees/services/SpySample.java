package com.howtodoinjava.employees.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpySample {

  private AutoCloseable closeable;

  @Mock
  private List<String> mockList;

  @Spy
  private List<String> spyList = new ArrayList<>();

  @BeforeEach
  public void init() {
    closeable = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  public void close() throws Exception {
    closeable.close();
  }

  @Test
  void testMockVsSpy() {
    spyList.add("one");
    spyList.add("two");

    verify(spyList).add("one");
    verify(spyList).add("two");

    assertEquals(2, spyList.size());

    doReturn(100).when(spyList).size();
    assertEquals(100, spyList.size());

    mockList.add("one");
    mockList.add("two");

    verify(mockList).add("one");
    verify(mockList).add("two");

    assertEquals(0, mockList.size());

    when(mockList.size()).thenReturn(100);

    assertEquals(100, mockList.size());

  }
}
