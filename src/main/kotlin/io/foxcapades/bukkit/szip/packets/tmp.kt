package io.foxcapades.bukkit.szip.packets

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.ListenerPriority
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.foxcapades.bukkit.szip.Logger
import io.foxcapades.bukkit.szip.Plugin
import io.foxcapades.bukkit.szip.ProtocolManager
import io.foxcapades.bukkit.szip.Server
import org.bukkit.configuration.serialization.ConfigurationSerialization

private inline fun <T> swallow(fn: () -> T): T? =
  try { fn() } catch (e: Throwable) { null }

private val gson = GsonBuilder()
  .setNumberToNumberStrategy {
    swallow { it.nextInt() }
      ?: swallow { it.nextLong() }
      ?: swallow { it.nextDouble() }
  }
  .create()

internal fun registerPacketListeners() {
  ProtocolManager.addPacketListener(object : PacketAdapter(
    Plugin,
    ListenerPriority.NORMAL,
    PacketType.Play.Client.USE_ITEM,
    PacketType.Play.Client.USE_ITEM_ON,
    PacketType.Play.Client.ENTITY_ACTION,
    PacketType.Play.Server.SET_SLOT,
    PacketType.Play.Server.TRANSFER,
    PacketType.Play.Server.HELD_ITEM_SLOT,
    PacketType.Play.Server.ENTITY_EQUIPMENT,
    PacketType.Play.Server.WINDOW_DATA,
    PacketType.Play.Server.WINDOW_ITEMS,
  ) {
    override fun onPacketSending(event: PacketEvent) {
      val item  = event.packet.itemModifier.readSafely(0)
      val type   = item?.type?.let(Server.itemFactory::getItemMeta)?.javaClass

      val meta = if (serial != null && type != null)
        try {
          ConfigurationSerialization.deserializeObject(
            gson.fromJson(serial, TypeToken.getParameterized(Map::class.java, String::class.java, Any::class.java)) as Map<String, Any?>,
            ConfigurationSerialization.getClassByAlias(ConfigurationSerialization.getAlias(type))!!
          )
        } catch (e: Exception) {
          e.message
        }
      else
        null


          Logger.trace("""      -
      <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
      
      Event  = $event
      Fields = ${event.packet.structures.fields.joinToString { it.field.toString() }}
      Values = ${try { event.packet.structures.values.joinToString { it.toString() }} catch (e: Throwable) { "ERROR: ${e.message}" }}
      Serial = ${gson.toJson(serial)}
      Type   = $type
      Meta   = $meta
      
      <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
      """.trimIndent())
//      event.packet.structures.values.ifExists(0) {
//        Logger.trace("==================================================================================================")
//        Logger.trace("%s")
//        Logger.trace("it = %s", it)
//        Logger.trace("target = %s", it.structures.targetType)
//        Logger.trace("fields = %s", it.structures.fields.joinToString { it.field.name })
//        Logger.trace("values = %s", it.structures.values.joinToString { it?.structures?.target?.javaClass?.name ?: "null" })
//      }

//      with(event.packet.structures.values[0]) {
//        Logger.trace("%s", this)
        //{minecraft:charged_projectiles=>ChargedProjectiles[items=[1 minecraft:arrow]], minecraft:damage=>0, minecraft:max_damage=>465, minecraft:rarity=>COMMON, minecraft:attribute_modifiers=>ItemAttributeModifiers[modifiers=[], showInTooltip=true], minecraft:repair_cost=>0, minecraft:max_stack_size=>1, minecraft:enchantments=>ItemEnchantments{enchantments={}, showInTooltip=true}, minecraft:lore=>ItemLore[lines=[], styledLines=[]]}
//
//        Logger.trace("0 > Field > Value > Type : %s", values[0].structures.targetType)
//        Logger.trace("0 > Field > Value > Data : %s", values[0].structures.target)
//
//        Logger.trace("1 > Field > Value > Type : %s", values[1].structures.targetType)
//        Logger.trace("1 > Field > Value > Data : %s", values[1].structures.target)
//      }
    }

    override fun onPacketReceiving(event: PacketEvent) {
      Logger.trace("""      -
      >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        
      Event      = $event
      TargetType = ${event.packet.modifier.targetType}
      Fields     = ${event.packet.modifier.fields.joinToString { it.field.toString() }}
        
      >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
      """.trimIndent())
//      with(event.packet.structures) {
//        Logger.trace("%s", this)
//        fields.forEachIndexed { index, fieldAccessor ->
//          Logger.trace("  %d", index)
//          Logger.trace("    Field %s", fieldAccessor.field)
//          with(values[index].structures) {
//            Logger.trace("    Value %s", this)
//            fields.forEachIndexed { index, fieldAccessor ->
//              Logger.trace("      %d", index)
//              Logger.trace("        Field %s", fieldAccessor.field)
//              Logger.trace("        Value %s", fieldAccessor.get(target))
//            }
//          }
//        }
//      }
    }

  })
}


/*
PacketEvent[
  player=CraftPlayer{name=ZoeDaniHarper},
  packet=PacketContainer[
    type=USE_ITEM[class=PacketPlayInUseItem, id=57],
    structureModifier=StructureModifier[
      fieldType=class java.lang.Object,
      data=[
        com.comphenix.protocol.reflect.accessors.DefaultFieldAccessor@38c395b0,
        com.comphenix.protocol.reflect.accessors.DefaultFieldAccessor@79706692,
        com.comphenix.protocol.reflect.accessors.DefaultFieldAccessor@1569e017,
        com.comphenix.protocol.reflect.accessors.DefaultFieldAccessor@51603a47
      ]
    ]
  ]
]
PacketContainer[
  type=USE_ITEM[class=PacketPlayInUseItem, id=57],
  structureModifier=StructureModifier[
    fieldType=class java.lang.Object,
    data=[
      com.comphenix.protocol.reflect.accessors.DefaultFieldAccessor@38c395b0,
      com.comphenix.protocol.reflect.accessors.DefaultFieldAccessor@79706692,
      com.comphenix.protocol.reflect.accessors.DefaultFieldAccessor@1569e017,
      com.comphenix.protocol.reflect.accessors.DefaultFieldAccessor@51603a47
    ]
  ]
]
StructureModifier[fieldType=class java.lang.Object, data=[com.comphenix.protocol.reflect.accessors.DefaultFieldAccessor@38c395b0, com.comphenix.protocol.reflect.accessors.DefaultFieldAccessor@79706692]]
 */
