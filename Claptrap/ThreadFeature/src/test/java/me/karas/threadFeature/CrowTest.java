package me.karas.threadFeature;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CrowTest {
    Crow crow = Mockito.mock(Crow.class);
    @BeforeAll
    void init() {
        Mockito.when(crow.show()).thenReturn("do it now");
    }

    @Test
    void show() {
        assertThat(crow.show()).isEqualTo("do it now");
    }
    @Test
    void showNative(){
        assertThat(new Crow().show()).isEqualTo("a strong crow");
    }
}