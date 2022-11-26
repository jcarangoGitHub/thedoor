package com.jca.thedoor;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({"com.jca.thedoor.util", "com.jca.thedoor.controllers"})
//@IncludeTags("develop")
public class AllTest {
}
