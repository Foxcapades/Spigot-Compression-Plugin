-verbose

-optimizationpasses 5

-libraryjars <java.home>/jmods/java.base.jmod(!**.jar;!module-info.class):<java.home>/jmods/java.logging.jmod(!**.jar;!module-info.class)

-keep class io.foxcapades.bukkit.szip.StackZip {
  public void onLoad();
  public void onEnable();
}

-keepclassmembers class io.foxcapades.bukkit.szip.event.EventDispatch {
  <methods>;
}

-keepclassmembers class io.foxcapades.bukkit.szip.config.ConfigValues {
  <fields>;
  <methods>;
}

-keepclassmembers class io.foxcapades.bukkit.szip.config.ZipToolConfigValues {
  <fields>;
  <methods>;
}

-keepclassmembers class io.foxcapades.bukkit.szip.config.RecipeConfigValues {
  <fields>;
  <methods>;
}

-keepattributes RuntimeVisibleAnnotations
