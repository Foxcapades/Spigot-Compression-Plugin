-verbose

-optimizationpasses 5

-libraryjars <java.home>/jmods/java.base.jmod(!**.jar;!module-info.class):<java.home>/jmods/java.logging.jmod(!**.jar;!module-info.class)

-keep class io.foxcapades.mc.bcp.BlockCompressionPlugin {
  public void onLoad();
  public void onEnable();
}

-keepclassmembers class io.foxcapades.mc.bcp.event.EventDispatch {
  <methods>;
}

-keepclassmembers class io.foxcapades.mc.bcp.config.ConfigValues {
  <fields>;
  <methods>;
}

-keepclassmembers class io.foxcapades.mc.bcp.config.ZipToolConfigValues {
  <fields>;
  <methods>;
}

-keepclassmembers class io.foxcapades.mc.bcp.config.RecipeConfigValues {
  <fields>;
  <methods>;
}

-keepattributes RuntimeVisibleAnnotations
