package com.jca.thedoor.proofOfConcept;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.condition.OS.LINUX;
import static org.junit.jupiter.api.condition.OS.WINDOWS;

//@Tag("produccion")
class OperacionesTest {


    @Test
    @Tag("produccion")
    void veriticarSuma2() {
        System.out.println("verifica suma2 producción");
    }

    @Test
    @Tag("develop")
    void veriticarresta() {
        System.out.println("verifica resta");
    }

    @Test
    //@EnabledOnOs({OS.LINUX})
    //@DisabledOnOs({LINUX, WINDOWS})
    //@EnabledOnJre(JRE.JAVA_15)
    //@EnabledForJreRange(min = JRE.JAVA_11, max = JRE.JAVA_14)
    //@EnabledIfSystemProperty(named = "os.name", matches = "Windowsxx 11")
    //@EnabledIfSystemProperty(named = "os.arch", matches = "x86_64")
    //@DisabledIfSystemProperty(named = "os.name", matches = "Windows 11")
    //@EnabledIfEnvironmentVariable(named = "CMDER_SHELL", matches = "cmd")
    //@DisabledIfEnvironmentVariable(named = "CMDER_SHELL", matches = "cmd")
    // TODO Create class to control conditions
    //@EnabledIf("com.jca.thedoor.util.OperacionesCondicion#mustBeRun")
    void verificarSuma() {
        System.out.println(System.getProperty("os.name"));
        System.out.println(System.getProperty("os.arch"));
        System.out.println("Test microsoft" + LINUX);

    }
}