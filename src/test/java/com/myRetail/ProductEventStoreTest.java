package com.myRetail;

import com.myRetail.service.ProductFetchService;
import com.myRetail.service.impl.ProductFetchServiceImpl;
import com.myRetail.service.impl.ProductUpdateServiceImpl;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class ProductEventStoreTest {

    @Mock
    EventStore eventStore;

    @InjectMocks
    ProductFetchServiceImpl productFetchService;

    @InjectMocks
    ProductUpdateServiceImpl productUpdateService;

    @Before
    public void setUp() {

    }
}
