package io.foxcapades.spigot.block.compression.test_utils

import io.mockk.mockk
import org.bukkit.*
import org.bukkit.BanList.Type
import org.bukkit.Server.Spigot
import org.bukkit.Warning.WarningState
import org.bukkit.advancement.Advancement
import org.bukkit.block.data.BlockData
import org.bukkit.boss.*
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.command.PluginCommand
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.generator.ChunkGenerator.ChunkData
import org.bukkit.help.HelpMap
import org.bukkit.inventory.*
import org.bukkit.loot.LootTable
import org.bukkit.map.MapView
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager
import org.bukkit.plugin.ServicesManager
import org.bukkit.plugin.messaging.Messenger
import org.bukkit.scheduler.BukkitScheduler
import org.bukkit.scoreboard.ScoreboardManager
import org.bukkit.structure.StructureManager
import org.bukkit.util.CachedServerIcon
import java.awt.image.BufferedImage
import java.io.File
import java.util.*
import java.util.function.Consumer
import java.util.logging.Logger


private typealias SendPluginMessageMock = (source: Plugin, channel: String, message: ByteArray) -> Unit

private typealias GetTagMock<T> = (registry: String, tag: NamespacedKey, clazz: Class<T>) -> Tag<T>


@Suppress("unused")
internal class MockServer : Server {


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockSendPluginMessage(fn: SendPluginMessageMock) {
    _sendPluginMessage = fn
  }

  private var _sendPluginMessage: SendPluginMessageMock = { _, _, _ -> TODO("Mock me!") }

  override fun sendPluginMessage(source: Plugin, channel: String, message: ByteArray) =
    _sendPluginMessage(source, channel, message)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetListeningPluginChannels(fn: () -> MutableSet<String>) {
    _getListeningPluginChannels = fn
  }

  private var _getListeningPluginChannels: () -> MutableSet<String> = { TODO("Mock me!") }

  override fun getListeningPluginChannels() = _getListeningPluginChannels()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetName(fn: () -> String) {
    _getName = fn
  }

  private var _getName: () -> String = { TODO("Mock me!") }

  override fun getName() = _getName()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetVersion(fn: () -> String) {
    _getVersion = fn
  }

  private var _getVersion: () -> String = { TODO("Mock me!") }

  override fun getVersion() = _getVersion()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetBukkitVersion(fn: () -> String) {
    _getBukkitVersion = fn
  }

  private var _getBukkitVersion: () -> String = { TODO("Mock me!") }

  override fun getBukkitVersion() = _getBukkitVersion()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetOnlinePlayers(fn: () -> MutableCollection<out Player>) {
    _getOnlinePlayers = fn
  }

  private var _getOnlinePlayers: () -> MutableCollection<out Player> = { TODO("Mock me!") }

  override fun getOnlinePlayers() = _getOnlinePlayers()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetMaxPlayers(fn: () -> Int) {
    _getMaxPlayers = fn
  }

  private var _getMaxPlayers: () -> Int = { TODO("Mock me!") }

  override fun getMaxPlayers() = _getMaxPlayers()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetPort(fn: () -> Int) {
    _getPort = fn
  }

  private var _getPort: () -> Int = { TODO("Mock me!") }

  override fun getPort() = _getPort()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetViewDistance(fn: () -> Int) {
    _getViewDistance = fn
  }

  private var _getViewDistance: () -> Int = { TODO("Mock me!") }

  override fun getViewDistance() = _getViewDistance()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetIp(fn: () -> String) {
    _getIp = fn
  }

  private var _getIp: () -> String = { TODO("Mock me!") }

  override fun getIp() = _getIp()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetWorldType(fn: () -> String) {
    _getWorldType = fn
  }

  private var _getWorldType: () -> String = { TODO("Mock me!") }

  override fun getWorldType() = _getWorldType()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetGenerateStructures(fn: () -> Boolean) {
    _getGenerateStructures = fn
  }

  private var _getGenerateStructures: () -> Boolean = { TODO("Mock me!") }

  override fun getGenerateStructures() = _getGenerateStructures()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetMaxWorldSize(fn: () -> Int) {
    _getMaxWorldSize = fn
  }

  private var _getMaxWorldSize: () -> Int = { TODO("Mock me!") }

  override fun getMaxWorldSize() = _getMaxWorldSize()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetAllowEnd(fn: () -> Boolean) {
    _getAllowEnd = fn
  }

  private var _getAllowEnd: () -> Boolean = { TODO("Mock me!") }

  override fun getAllowEnd() = _getAllowEnd()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetAllowNether(fn: () -> Boolean) {
    _getAllowNether = fn
  }

  private var _getAllowNether: () -> Boolean = { TODO("Mock me!") }

  override fun getAllowNether() = _getAllowNether()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockHasWhitelist(fn: () -> Boolean) {
    _hasWhitelist = fn
  }

  private var _hasWhitelist: () -> Boolean = { TODO("Mock me!") }

  override fun hasWhitelist() = _hasWhitelist()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockSetWhitelist(fn: (value: Boolean) -> Unit) {
    _setWhitelist = fn
  }

  private var _setWhitelist: (Boolean) -> Unit = { TODO("Mock me!") }

  override fun setWhitelist(value: Boolean) = _setWhitelist(value)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockIsWhitelistEnforced(fn: () -> Boolean) {
    _isWhitelistEnforced = fn
  }

  private var _isWhitelistEnforced: () -> Boolean = { TODO("Mock me!") }

  override fun isWhitelistEnforced() = _isWhitelistEnforced()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockSetWhitelistEnforced(fn: (value: Boolean) -> Unit) {
    _setWhitelistEnforced = fn
  }

  private var _setWhitelistEnforced: (Boolean) -> Unit = { TODO("Mock me!") }

  override fun setWhitelistEnforced(value: Boolean) = _setWhitelistEnforced(value)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetWhitelistedPlayers(fn: () -> MutableSet<OfflinePlayer>) {
    _getWhitelistedPlayers = fn
  }

  private var _getWhitelistedPlayers: () -> MutableSet<OfflinePlayer> = { TODO("Mock me!") }

  override fun getWhitelistedPlayers() = _getWhitelistedPlayers()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockReloadWhitelist(fn: () -> Unit) {
    _reloadWhitelist = fn
  }

  private var _reloadWhitelist: () -> Unit = { TODO("Mock me!") }

  override fun reloadWhitelist() = _reloadWhitelist()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockBroadcastMessage(fn: (message: String) -> Int) {
    _broadcastMessage = fn
  }

  private var _broadcastMessage: (String) -> Int = { _ -> TODO("Mock me!") }

  override fun broadcastMessage(message: String) = _broadcastMessage(message)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetUpdateFolder(fn: () -> String) {
    _getUpdateFolder = fn
  }

  private var _getUpdateFolder: () -> String = { TODO("Mock me!") }

  override fun getUpdateFolder() = _getUpdateFolder()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetUpdateFolderFile(fn: () -> File) {
    _getUpdateFolderFile = fn
  }

  private var _getUpdateFolderFile: () -> File = { TODO("Mock me!") }

  override fun getUpdateFolderFile() = _getUpdateFolderFile()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetConnectionThrottle(fn: () -> Long) {
    _getConnectionThrottle = fn
  }

  private var _getConnectionThrottle: () -> Long = { TODO("Mock me!") }

  override fun getConnectionThrottle() = _getConnectionThrottle()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetTicksPerAnimalSpawns(fn: () -> Int) {
    _getTicksPerAnimalSpawns = fn
  }

  private var _getTicksPerAnimalSpawns: () -> Int = { TODO("Mock me!") }

  override fun getTicksPerAnimalSpawns() = _getTicksPerAnimalSpawns()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetTicksPerMonsterSpawns(fn: () -> Int) {
    _getTicksPerMonsterSpawns = fn
  }

  private var _getTicksPerMonsterSpawns: () -> Int = { TODO("Mock me!") }

  override fun getTicksPerMonsterSpawns() = _getTicksPerMonsterSpawns()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetTicksPerWaterSpawns(fn: () -> Int) {
    _getTicksPerWaterSpawns = fn
  }

  private var _getTicksPerWaterSpawns: () -> Int = { TODO("Mock me!") }

  override fun getTicksPerWaterSpawns() = _getTicksPerWaterSpawns()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetTicksPerWaterAmbientSpawns(fn: () -> Int) {
    _getTicksPerWaterAmbientSpawns = fn
  }

  private var _getTicksPerWaterAmbientSpawns: () -> Int = { TODO("Mock me!") }

  override fun getTicksPerWaterAmbientSpawns() = _getTicksPerWaterAmbientSpawns()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetTicksPerWaterUndergroundCreatureSpawns(fn: () -> Int) {
    _getTicksPerWaterUndergroundCreatureSpawns = fn
  }

  private var _getTicksPerWaterUndergroundCreatureSpawns: () -> Int = { TODO("Mock me!") }

  override fun getTicksPerWaterUndergroundCreatureSpawns() = _getTicksPerWaterUndergroundCreatureSpawns()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetTicksPerAmbientSpawns(fn: () -> Int) {
    _getTicksPerAmbientSpawns = fn
  }

  private var _getTicksPerAmbientSpawns: () -> Int = { TODO("Mock me!") }

  override fun getTicksPerAmbientSpawns() = _getTicksPerAmbientSpawns()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetPlayerByName(fn: (name: String) -> Player?) {
    _getPlayer1 = fn
  }

  private var _getPlayer1: (String) -> Player? = { _ -> TODO("Mock me!") }

  override fun getPlayer(name: String) = _getPlayer1(name)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetPlayerByID(fn: (id: UUID) -> Player?) {
    _getPlayer2 = fn
  }

  private var _getPlayer2: (UUID) -> Player? = { _ -> TODO("Mock me!") }

  override fun getPlayer(id: UUID) = _getPlayer2(id)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetPlayerExact(fn: (name: String) -> Player?) {
    _getPlayerExact = fn
  }

  private var _getPlayerExact: (String) -> Player? = { _ -> TODO("Mock me!") }

  override fun getPlayerExact(name: String) = _getPlayerExact(name)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockMatchPlayer(fn: (name: String) -> MutableList<Player>) {
    _matchPlayer = fn
  }

  private var _matchPlayer: (String) -> MutableList<Player> = { _ -> TODO("Mock me!") }

  override fun matchPlayer(name: String) = _matchPlayer(name)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetPluginManager(fn: () -> PluginManager) {
    _getPluginManager = fn
  }

  private var _getPluginManager: () -> PluginManager = { TODO("Mock me!") }

  override fun getPluginManager() = _getPluginManager()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetScheduler(fn: () -> BukkitScheduler) {
    _getScheduler = fn
  }

  private var _getScheduler: () -> BukkitScheduler = { TODO("Mock me!") }

  override fun getScheduler() = _getScheduler()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetServicesManager(fn: () -> ServicesManager) {
    _getServicesManager = fn
  }

  private var _getServicesManager: () -> ServicesManager = { TODO("Mock me!") }

  override fun getServicesManager() = _getServicesManager()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetWorlds(fn: () -> MutableList<World>) {
    _getWorlds = fn
  }

  private var _getWorlds: () -> MutableList<World> = { TODO("Mock me!") }

  override fun getWorlds() = _getWorlds()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockCreateWorld(fn: (creator: WorldCreator) -> World?) {
    _createWorld = fn
  }

  private var _createWorld: (WorldCreator) -> World? = { _ -> TODO("Mock me!") }

  override fun createWorld(creator: WorldCreator) = _createWorld(creator)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockUnloadWorldByName(fn: (name: String, save: Boolean) -> Boolean) {
    _unloadWorld1 = fn
  }

  private var _unloadWorld1: (String, Boolean) -> Boolean = { _, _ -> TODO("Mock me!") }

  override fun unloadWorld(name: String, save: Boolean) = _unloadWorld1(name, save)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockUnloadWorldByWorld(fn: (world: World, save: Boolean) -> Boolean) {
    _unloadWorld2 = fn
  }

  private var _unloadWorld2: (World, Boolean) -> Boolean = { _, _ -> TODO("Mock me!") }

  override fun unloadWorld(world: World, save: Boolean) = _unloadWorld2(world, save)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetWorldByName(fn: (name: String) -> World?) {
    _getWorld1 = fn
  }

  private var _getWorld1: (String) -> World? = { _ -> TODO("Mock me!") }

  override fun getWorld(name: String) = _getWorld1(name)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetWorldByUUID(fn: (uid: UUID) -> World?) {
    _getWorld2 = fn
  }

  private var _getWorld2: (UUID) -> World? = { _ -> TODO("Mock me!") }

  override fun getWorld(uid: UUID) = _getWorld2(uid)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetMap(fn: (id: Int) -> MapView?) {
    _getMap = fn
  }

  private var _getMap: (Int) -> MapView? = { _ -> TODO("Mock me!") }

  override fun getMap(id: Int) = _getMap(id)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockCreateMap(fn: (world: World) -> MapView) {
    _createMap = fn
  }

  private var _createMap: (World) -> MapView = { _ -> TODO("Mock me!") }

  override fun createMap(world: World) = _createMap(world)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockCreateExplorerMapShort(fn: (world: World, location: Location, structureType: StructureType) -> ItemStack) {
    _createExplorerMap = fn
  }

  private var _createExplorerMap: (World, Location, StructureType) -> ItemStack = { _, _, _ -> TODO("Mock me!") }

  override fun createExplorerMap(world: World, location: Location, structureType: StructureType) =
    _createExplorerMap(world, location, structureType)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  @FunctionalInterface
  interface CreateExplorerMapMock {
    fun call(
      world: World,
      location: Location,
      structureType: StructureType,
      radius: Int,
      findUnexplored: Boolean,
    ): ItemStack
  }

  fun mockCreateExplorerMap(fn: CreateExplorerMapMock) { _createExplorerMap2 = fn }

  private var _createExplorerMap2: CreateExplorerMapMock =
    { _: World, _: Location, _: StructureType, _: Int, _: Boolean -> TODO("Mock me!") } as CreateExplorerMapMock

  override fun createExplorerMap(
    world: World,
    location: Location,
    structureType: StructureType,
    radius: Int,
    findUnexplored: Boolean,
  ): ItemStack = _createExplorerMap2.call(world, location, structureType, radius, findUnexplored)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockReload(fn: () -> Unit) {
    _reload = fn
  }

  private var _reload: () -> Unit = { TODO("Mock me!") }

  override fun reload() = _reload()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockReloadData(fn: () -> Unit) {
    _reloadData = fn
  }

  private var _reloadData: () -> Unit = { TODO("Mock me!") }

  override fun reloadData() = _reloadData()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetLogger(fn: () -> Logger) {
    _getLogger = fn
  }

  private var _getLogger: () -> Logger = { TODO("Mock me!") }

  override fun getLogger() = _getLogger()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetPluginCommand(fn: (name: String) -> PluginCommand?) {
    _getPluginCommand = fn
  }

  private var _getPluginCommand: (String) -> PluginCommand? = { _ -> TODO("Mock me!") }

  override fun getPluginCommand(name: String) = _getPluginCommand(name)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockSavePlayers(fn: () -> Unit) {
    _savePlayers = fn
  }

  private var _savePlayers: () -> Unit = { TODO("Mock me!") }

  override fun savePlayers() = _savePlayers()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockDispatchCommand(fn: (sender: CommandSender, commandLine: String) -> Boolean) {
    _dispatchCommand = fn
  }

  private var _dispatchCommand: (CommandSender, String) -> Boolean = { _, _ -> TODO("Mock me!") }

  override fun dispatchCommand(sender: CommandSender, commandLine: String) =
    _dispatchCommand(sender, commandLine)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockAddRecipe(fn: (recipe: Recipe?) -> Boolean) {
    _addRecipe = fn
  }

  private var _addRecipe: (Recipe?) -> Boolean = { _ -> TODO("Mock me!") }

  override fun addRecipe(recipe: Recipe?) = _addRecipe(recipe)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetRecipesFor(fn: (result: ItemStack) -> MutableList<Recipe>) {
    _getRecipesFor = fn
  }

  private var _getRecipesFor: (ItemStack) -> MutableList<Recipe> = { _ -> TODO("Mock me!") }

  override fun getRecipesFor(result: ItemStack) = _getRecipesFor(result)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetRecipe(fn: (recipeKey: NamespacedKey) -> Recipe?) {
    _getRecipe = fn
  }

  private var _getRecipe: (NamespacedKey) -> Recipe? = { _ -> TODO("Mock me!") }

  override fun getRecipe(recipeKey: NamespacedKey) = _getRecipe(recipeKey)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetCraftingRecipe(fn: (craftingMatrix: Array<out ItemStack>, world: World) -> Recipe?) {
    _getCraftingRecipe = fn
  }

  private var _getCraftingRecipe: (Array<out ItemStack>, World) -> Recipe? = { _, _ -> TODO("Mock me!") }

  override fun getCraftingRecipe(craftingMatrix: Array<out ItemStack>, world: World) =
    _getCraftingRecipe(craftingMatrix, world)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockCraftItem(fn: (craftingMatrix: Array<out ItemStack>, world: World, player: Player) -> ItemStack) {
    _craftItem = fn
  }

  private var _craftItem: (Array<out ItemStack>, World, Player) -> ItemStack = { _, _, _ -> TODO("Mock me!") }

  override fun craftItem(craftingMatrix: Array<out ItemStack>, world: World, player: Player) =
    _craftItem(craftingMatrix, world, player)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockRecipeIterator(fn: () -> MutableIterator<Recipe>) {
    _recipeIterator = fn
  }

  private var _recipeIterator: () -> MutableIterator<Recipe> = { TODO("Mock me!") }

  override fun recipeIterator() = _recipeIterator()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockClearRecipes(fn: () -> Unit) {
    _clearRecipes = fn
  }

  private var _clearRecipes: () -> Unit = { TODO("Mock me!") }

  override fun clearRecipes() = _clearRecipes()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockResetRecipes(fn: () -> Unit) {
    _resetRecipes = fn
  }

  private var _resetRecipes: () -> Unit = { TODO("Mock me!") }

  override fun resetRecipes() = _resetRecipes()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockRemoveRecipe(fn: (key: NamespacedKey) -> Boolean) {
    _removeRecipe = fn
  }

  private var _removeRecipe: (NamespacedKey) -> Boolean = { _ -> TODO("Mock me!") }

  override fun removeRecipe(key: NamespacedKey) = _removeRecipe(key)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetCommandAliases(fn: () -> MutableMap<String, Array<String>>) {
    _getCommandAliases = fn
  }

  private var _getCommandAliases: () -> MutableMap<String, Array<String>> = { TODO("Mock me!") }

  override fun getCommandAliases() = _getCommandAliases()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetSpawnRadius(fn: () -> Int) {
    _getSpawnRadius = fn
  }

  private var _getSpawnRadius: () -> Int = { TODO("Mock me!") }

  override fun getSpawnRadius() = _getSpawnRadius()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockSetSpawnRadius(fn: (value: Int) -> Unit) {
    _setSpawnRadius = fn
  }

  private var _setSpawnRadius: (Int) -> Unit = { TODO("Mock me!") }

  override fun setSpawnRadius(value: Int) = _setSpawnRadius(value)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetOnlineMode(fn: () -> Boolean) {
    _getOnlineMode = fn
  }

  private var _getOnlineMode: () -> Boolean = { TODO("Mock me!") }

  override fun getOnlineMode() = _getOnlineMode()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetAllowFlight(fn: () -> Boolean) {
    _getAllowFlight = fn
  }

  private var _getAllowFlight: () -> Boolean = { TODO("Mock me!") }

  override fun getAllowFlight() = _getAllowFlight()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockIsHardcore(fn: () -> Boolean) {
    _isHardcore = fn
  }

  private var _isHardcore: () -> Boolean = { TODO("Mock me!") }

  override fun isHardcore() = _isHardcore()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockShutdown(fn: () -> Unit) {
    _shutdown = fn
  }

  private var _shutdown: () -> Unit = { TODO("Mock me!") }

  override fun shutdown() = _shutdown()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockBroadcast(fn: (message: String, permission: String) -> Int) {
    _broadcast = fn
  }

  private var _broadcast: (String, String) -> Int = { _, _ -> TODO("Mock me!") }

  override fun broadcast(message: String, permission: String) = _broadcast(message, permission)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetOfflinePlayerByName(fn: (name: String) -> OfflinePlayer) {
    _getOfflinePlayer1 = fn
  }

  private var _getOfflinePlayer1: (String) -> OfflinePlayer = { _ -> TODO("Mock me!") }

  override fun getOfflinePlayer(name: String) = _getOfflinePlayer1(name)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetOfflinePlayerByUUID(fn: (id: UUID) -> OfflinePlayer) {
    _getOfflinePlayer2 = fn
  }

  private var _getOfflinePlayer2: (UUID) -> OfflinePlayer = { _ -> TODO("Mock me!") }

  override fun getOfflinePlayer(id: UUID) = _getOfflinePlayer2(id)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetIPBans(fn: () -> MutableSet<String>) {
    _getIPBans = fn
  }

  private var _getIPBans: () -> MutableSet<String> = { TODO("Mock me!") }

  override fun getIPBans() = _getIPBans()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockBanIP(fn: (address: String) -> Unit) {
    _banIP = fn
  }

  private var _banIP: (String) -> Unit = { TODO("Mock me!") }

  override fun banIP(address: String) = _banIP(address)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockUnbanIP(fn: (address: String) -> Unit) {
    _unbanIP = fn
  }

  private var _unbanIP: (String) -> Unit = { TODO("Mock me!") }

  override fun unbanIP(address: String) = _unbanIP(address)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetBannedPlayers(fn: () -> MutableSet<OfflinePlayer>) {
    _getBannedPlayers = fn
  }

  private var _getBannedPlayers: () -> MutableSet<OfflinePlayer> = { TODO("Mock me!") }

  override fun getBannedPlayers() = _getBannedPlayers()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetBanList(fn: (type: Type) -> BanList) {
    _getBanList = fn
  }

  private var _getBanList: (Type) -> BanList = { _ -> TODO("Mock me!") }

  override fun getBanList(type: Type) = _getBanList(type)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetOperators(fn: () -> MutableSet<OfflinePlayer>) {
    _getOperators = fn
  }

  private var _getOperators: () -> MutableSet<OfflinePlayer> = { TODO("Mock me!") }

  override fun getOperators() = _getOperators()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetDefaultGameMode(fn: () -> GameMode) {
    _getDefaultGameMode = fn
  }

  private var _getDefaultGameMode: () -> GameMode = { TODO("Mock me!") }

  override fun getDefaultGameMode() = _getDefaultGameMode()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockSetDefaultGameMode(fn: (mode: GameMode) -> Unit) {
    _setDefaultGameMode = fn
  }

  private var _setDefaultGameMode: (GameMode) -> Unit = { TODO("Mock me!") }

  override fun setDefaultGameMode(mode: GameMode) = _setDefaultGameMode(mode)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetConsoleSender(fn: () -> ConsoleCommandSender) {
    _getConsoleSender = fn
  }

  private var _getConsoleSender: () -> ConsoleCommandSender = { TODO("Mock me!") }

  override fun getConsoleSender() = _getConsoleSender()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetWorldContainer(fn: () -> File) {
    _getWorldContainer = fn
  }

  private var _getWorldContainer: () -> File = { TODO("Mock me!") }

  override fun getWorldContainer() = _getWorldContainer()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetOfflinePlayers(fn: () -> Array<OfflinePlayer>) {
    _getOfflinePlayers = fn
  }

  private var _getOfflinePlayers: () -> Array<OfflinePlayer> = { TODO("Mock me!") }

  override fun getOfflinePlayers() = _getOfflinePlayers()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetMessenger(fn: () -> Messenger) {
    _getMessenger = fn
  }

  private var _getMessenger: () -> Messenger = { TODO("Mock me!") }

  override fun getMessenger() = _getMessenger()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetHelpMap(fn: () -> HelpMap) {
    _getHelpMap = fn
  }

  private var _getHelpMap: () -> HelpMap = { TODO("Mock me!") }

  override fun getHelpMap() = _getHelpMap()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockCreateInventoryWithType(fn: (owner: InventoryHolder?, type: InventoryType) -> Inventory) {
    _createInventory1 = fn
  }

  private var _createInventory1: (InventoryHolder?, InventoryType) -> Inventory = { _, _ -> TODO("Mock me!") }

  override fun createInventory(owner: InventoryHolder?, type: InventoryType) = _createInventory1(owner, type)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockCreateInventoryWithTypeAndTitle(fn: (owner: InventoryHolder?, type: InventoryType, title: String) -> Inventory) {
    _createInventory2 = fn
  }

  private var _createInventory2: (InventoryHolder?, InventoryType, String) -> Inventory =
    { _, _, _ -> TODO("Mock me!") }

  override fun createInventory(owner: InventoryHolder?, type: InventoryType, title: String) =
    _createInventory2(owner, type, title)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockCreateInventoryWithSize(fn: (owner: InventoryHolder?, size: Int) -> Inventory) {
    _createInventory3 = fn
  }

  private var _createInventory3: (InventoryHolder?, Int) -> Inventory = { _, _ -> TODO("Mock me!") }

  override fun createInventory(owner: InventoryHolder?, size: Int) = _createInventory3(owner, size)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockCreateInventoryWithSizeAndTitle(fn: (owner: InventoryHolder?, size: Int, title: String) -> Inventory) {
    _createInventory4 = fn
  }

  private var _createInventory4: (InventoryHolder?, Int, String) -> Inventory = { _, _, _ -> TODO("Mock me!") }

  override fun createInventory(owner: InventoryHolder?, size: Int, title: String) =
    _createInventory4(owner, size, title)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockCreateMerchant(fn: (title: String?) -> Merchant) {
    _createMerchant = fn
  }

  private var _createMerchant: (String?) -> Merchant = { _ -> TODO("Mock me!") }

  override fun createMerchant(title: String?) = _createMerchant(title)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetMonsterSpawnLimit(fn: () -> Int) {
    _getMonsterSpawnLimit = fn
  }

  private var _getMonsterSpawnLimit: () -> Int = { TODO("Mock me!") }

  override fun getMonsterSpawnLimit() = _getMonsterSpawnLimit()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetAnimalSpawnLimit(fn: () -> Int) {
    _getAnimalSpawnLimit = fn
  }

  private var _getAnimalSpawnLimit: () -> Int = { TODO("Mock me!") }

  override fun getAnimalSpawnLimit() = _getAnimalSpawnLimit()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetWaterAnimalSpawnLimit(fn: () -> Int) {
    _getWaterAnimalSpawnLimit = fn
  }

  private var _getWaterAnimalSpawnLimit: () -> Int = { TODO("Mock me!") }

  override fun getWaterAnimalSpawnLimit() = _getWaterAnimalSpawnLimit()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetWaterAmbientSpawnLimit(fn: () -> Int) {
    _getWaterAmbientSpawnLimit = fn
  }

  private var _getWaterAmbientSpawnLimit: () -> Int = { TODO("Mock me!") }

  override fun getWaterAmbientSpawnLimit() = _getWaterAmbientSpawnLimit()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetWaterUndergroundCreatureSpawnLimit(fn: () -> Int) {
    _getWaterUndergroundCreatureSpawnLimit = fn
  }

  private var _getWaterUndergroundCreatureSpawnLimit: () -> Int = { TODO("Mock me!") }

  override fun getWaterUndergroundCreatureSpawnLimit() = _getWaterUndergroundCreatureSpawnLimit()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetAmbientSpawnLimit(fn: () -> Int) {
    _getAmbientSpawnLimit = fn
  }

  private var _getAmbientSpawnLimit: () -> Int = { TODO("Mock me!") }

  override fun getAmbientSpawnLimit() = _getAmbientSpawnLimit()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockIsPrimaryThread(fn: () -> Boolean) {
    _isPrimaryThread = fn
  }

  private var _isPrimaryThread: () -> Boolean = { TODO("Mock me!") }

  override fun isPrimaryThread() = _isPrimaryThread()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetMotd(fn: () -> String) {
    _getMotd = fn
  }

  private var _getMotd: () -> String = { TODO("Mock me!") }

  override fun getMotd() = _getMotd()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetShutdownMessage(fn: () -> String?) {
    _getShutdownMessage = fn
  }

  private var _getShutdownMessage: () -> String? = { TODO("Mock me!") }

  override fun getShutdownMessage() = _getShutdownMessage()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetWarningState(fn: () -> WarningState) {
    _getWarningState = fn
  }

  private var _getWarningState: () -> WarningState = { TODO("Mock me!") }

  override fun getWarningState() = _getWarningState()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetItemFactory(fn: () -> ItemFactory) {
    _getItemFactory = fn
  }

  private var _getItemFactory: () -> ItemFactory = { TODO("Mock me!") }

  override fun getItemFactory() = _getItemFactory()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetScoreboardManager(fn: () -> ScoreboardManager?) {
    _getScoreboardManager = fn
  }

  private var _getScoreboardManager: () -> ScoreboardManager? = { TODO("Mock me!") }

  override fun getScoreboardManager() = _getScoreboardManager()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetServerIcon(fn: () -> CachedServerIcon?) {
    _getServerIcon = fn
  }

  private var _getServerIcon: () -> CachedServerIcon? = { TODO("Mock me!") }

  override fun getServerIcon() = _getServerIcon()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockLoadServerIconFromFile(fn: (file: File) -> CachedServerIcon) {
    _loadServerIcon1 = fn
  }

  private var _loadServerIcon1: (File) -> CachedServerIcon = { _ -> TODO("Mock me!") }

  override fun loadServerIcon(file: File) = _loadServerIcon1(file)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockLoadServerIconFromImage(fn: (image: BufferedImage) -> CachedServerIcon) {
    _loadServerIcon2 = fn
  }

  private var _loadServerIcon2: (BufferedImage) -> CachedServerIcon = { _ -> TODO("Mock me!") }

  override fun loadServerIcon(image: BufferedImage) = _loadServerIcon2(image)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockSetIdleTimeout(fn: (threshold: Int) -> Unit) {
    _setIdleTimeout = fn
  }

  private var _setIdleTimeout: (Int) -> Unit = { TODO("Mock me!") }

  override fun setIdleTimeout(threshold: Int) = _setIdleTimeout(threshold)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetIdleTimeout(fn: () -> Int) {
    _getIdleTimeout = fn
  }

  private var _getIdleTimeout: () -> Int = { TODO("Mock me!") }

  override fun getIdleTimeout() = _getIdleTimeout()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockCreateChunkData(fn: (world: World) -> ChunkData) {
    _createChunkData = fn
  }

  private var _createChunkData: (World) -> ChunkData = { _ -> TODO("Mock me!") }

  override fun createChunkData(world: World) = _createChunkData(world)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  @FunctionalInterface
  interface ShortBossBarFactory {
    fun call(title: String?, color: BarColor, style: BarStyle, vararg flags: BarFlag?): BossBar
  }

  fun mockCreateBossBar(fn: ShortBossBarFactory) {
    _createBossBar1 = fn
  }

  private var _createBossBar1: ShortBossBarFactory =
    { _: String?, _: BarColor, _: BarStyle, _: Array<BarFlag?> -> TODO("Mock me!") } as ShortBossBarFactory

  override fun createBossBar(title: String?, color: BarColor, style: BarStyle, vararg flags: BarFlag?) =
    _createBossBar1.call(title, color, style, *flags)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  @FunctionalInterface
  interface LongBossBarFactory {
    fun call(
      key: NamespacedKey,
      title: String?,
      color: BarColor,
      style: BarStyle,
      flags: Array<out BarFlag?>,
    ): KeyedBossBar
  }

  fun mockCreateBossBar(fn: LongBossBarFactory) {
    _createBossBar2 = fn
  }

  private var _createBossBar2: LongBossBarFactory =
    { _: NamespacedKey, _: String?, _: BarColor, _: BarStyle, _: Array<BarFlag?> ->
      TODO("Mock me!")
    } as LongBossBarFactory

  override fun createBossBar(
    key: NamespacedKey,
    title: String?,
    color: BarColor,
    style: BarStyle,
    vararg flags: BarFlag?,
  ) = _createBossBar2.call(key, title, color, style, flags)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetBossBars(fn: () -> MutableIterator<KeyedBossBar>) {
    _getBossBars = fn
  }

  private var _getBossBars: () -> MutableIterator<KeyedBossBar> = { TODO("Mock me!") }

  override fun getBossBars() = _getBossBars()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetBossBar(fn: (key: NamespacedKey) -> KeyedBossBar?) {
    _getBossBar = fn
  }

  private var _getBossBar: (NamespacedKey) -> KeyedBossBar? = { _ -> TODO("Mock me!") }

  override fun getBossBar(key: NamespacedKey) = _getBossBar(key)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockRemoveBossBar(fn: (key: NamespacedKey) -> Boolean) {
    _removeBossBar = fn
  }

  private var _removeBossBar: (NamespacedKey) -> Boolean = { _ -> TODO("Mock me!") }

  override fun removeBossBar(key: NamespacedKey) = _removeBossBar(key)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetEntity(fn: (uuid: UUID) -> Entity?) {
    _getEntity = fn
  }

  private var _getEntity: (UUID) -> Entity? = { _ -> TODO("Mock me!") }

  override fun getEntity(uuid: UUID) = _getEntity(uuid)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetAdvancement(fn: (key: NamespacedKey) -> Advancement?) {
    _getAdvancement = fn
  }

  private var _getAdvancement: (NamespacedKey) -> Advancement? = { _ -> TODO("Mock me!") }

  override fun getAdvancement(key: NamespacedKey) = _getAdvancement(key)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockAdvancementIterator(fn: () -> MutableIterator<Advancement>) {
    _advancementIterator = fn
  }

  private var _advancementIterator: () -> MutableIterator<Advancement> = { TODO("Mock me!") }

  override fun advancementIterator() = _advancementIterator()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockCreateBlockDataFromMaterial(fn: (material: Material) -> BlockData) {
    _createBlockData1 = fn
  }

  private var _createBlockData1: (Material) -> BlockData = { _ -> TODO("Mock me!") }

  override fun createBlockData(material: Material) = _createBlockData1(material)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockCreateBlockDataConsumer(fn: (material: Material, consumer: Consumer<BlockData>?) -> BlockData) {
    _createBlockData2 = fn
  }

  private var _createBlockData2: (Material, Consumer<BlockData>?) -> BlockData = { _, _ -> TODO("Mock me!") }

  override fun createBlockData(material: Material, consumer: Consumer<BlockData>?) =
    _createBlockData2(material, consumer)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockCreateBlockDataFromString(fn: (data: String) -> BlockData) {
    _createBlockData3 = fn
  }

  private var _createBlockData3: (String) -> BlockData = { _ -> TODO("Mock me!") }

  override fun createBlockData(data: String) = _createBlockData3(data)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockCreateBlockDataString(fn: (material: Material?, data: String?) -> BlockData) {
    _createBlockData4 = fn
  }

  private var _createBlockData4: (Material?, String?) -> BlockData = { _, _ -> TODO("Mock me!") }

  override fun createBlockData(material: Material?, data: String?) = _createBlockData4(material, data)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  @Suppress("UNCHECKED_CAST")
  fun <T: Keyed> mockGetTag(fn: GetTagMock<T>) {
    _tag = fn as GetTagMock<*>
  }

  private var _tag: GetTagMock<*> = { _, _, _ -> TODO("Mock me!") }

  @Suppress("UNCHECKED_CAST")
  override fun <T : Keyed?> getTag(registry: String, tag: NamespacedKey, clazz: Class<T>) =
    _tag(registry, tag, clazz) as Tag<T>


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  @Suppress("UNCHECKED_CAST")
  fun <T: Keyed> mockGetTags(fn: (registry: String, clazz: Class<T>) -> MutableIterator<Tag<T>>) {
    _tags = fn as (String, Class<*>) -> MutableIterator<Tag<*>>
  }

  private var _tags: (String, Class<*>) -> MutableIterator<Tag<*>> = { _, _ -> TODO("Mock me!") }

  override fun <T : Keyed?> getTags(registry: String, clazz: Class<T>): MutableIterable<Tag<T>> {
    TODO("Not yet implemented")
  }


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetLootTable(fn: (key: NamespacedKey) -> LootTable?) {
    _getLootTable = fn
  }

  private var _getLootTable: (NamespacedKey) -> LootTable? = { _ -> TODO("Mock me!") }

  override fun getLootTable(key: NamespacedKey) = _getLootTable(key)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockSelectEntities(fn: (sender: CommandSender, selector: String) -> MutableList<Entity>) {
    _selectEntities = fn
  }

  private var _selectEntities: (CommandSender, String) -> MutableList<Entity> = { _, _ -> TODO("Mock me!") }

  override fun selectEntities(sender: CommandSender, selector: String) = _selectEntities(sender, selector)


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetStructureManager(fn: () -> StructureManager) {
    _getStructureManager = fn
  }

  private var _getStructureManager: () -> StructureManager = { TODO("Mock me!") }

  override fun getStructureManager() = _getStructureManager()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  fun mockGetUnsafe(fn: () -> UnsafeValues) {
    _unsafe = fn
  }

  private var _unsafe: () -> UnsafeValues = { mockk() }

  override fun getUnsafe() = _unsafe()


  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  fun mockSpigot(fn: () -> Spigot) {
    _spigot = fn
  }

  private var _spigot: () -> Spigot = { mockk() }

  override fun spigot() = _spigot()
}
