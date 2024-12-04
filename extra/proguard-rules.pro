-verbose

-optimizationpasses 5

-libraryjars <java.home>/jmods/java.base.jmod(!**.jar;!module-info.class):<java.home>/jmods/java.logging.jmod(!**.jar;!module-info.class)

-keep class io.foxcapades.spigot.bcp.BlockCompressionPlugin {
  public void onLoad();
  public void onEnable();
}

-keep class io.foxcapades.spigot.bcp.event.EventDispatch {
  public void onBlockPlace(...);
  public void onInventoryClick(...);
  public void onInventoryDrag(...);
  public void onInventoryClose(...);
  public void onPlayerInteract(...);
  public void onPlayerJoin(...);
}

-keep class io.foxcapades.spigot.bcp.config.ConfigValues {
  public void setVersion(java.lang.String);
  public java.lang.String getVersion();

  public void setTraceEnabled(boolean);
  public boolean getTraceEnabled();

  public void setLocale(java.lang.String);
  public java.lang.String getLocale();

  public void setZipTool(io.foxcapades.spigot.bcp.config.ZipToolConfigValues);
  public io.foxcapades.spigot.bcp.config.ZipToolConfigValues getZipTool();
}

-keep class io.foxcapades.spigot.bcp.config.ZipToolConfigValues {
  public void setTexture(java.lang.String);
  public java.lang.String getTexture();

  public void setRecipe(io.foxcapades.spigot.bcp.config.RecipeConfigValues);
  public io.foxcapades.spigot.bcp.config.RecipeConfigValues getRecipe();
}

-keep class io.foxcapades.spigot.bcp.config.RecipeConfigValues {
  public void setItems(java.util.Map);
  public java.util.Map getItems();

  public void setLayout(java.lang.String);
  public java.lang.String getLayout();
}

-keepattributes RuntimeVisibleAnnotations
