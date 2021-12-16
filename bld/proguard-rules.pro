-verbose

-optimizationpasses 5

-libraryjars <java.home>/jmods/java.base.jmod(!**.jar;!module-info.class):<java.home>/jmods/java.logging.jmod(!**.jar;!module-info.class)

-keep class io.foxcapades.spigot.block.compression.BlockCompressionPlugin {
  public void onLoad();
  public void onEnable();
}

-keep class io.foxcapades.spigot.block.compression.command.CompressExecutor {
  public *** onCommand(...);
}

-keep class io.foxcapades.spigot.block.compression.event.EventDispatch {
  public void onInventoryClick(...);
  public void onInventoryDrag(...);
  public void onInventoryClose(...);
  public void onBlockPlace(...);
  public void onPlayerInteract(...);
}

-keepattributes RuntimeVisibleAnnotations