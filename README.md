# Create: Recipe Need RPM

[한국어 README](README_KO.md)

A Minecraft 1.21.1 NeoForge addon for Create that adds minimum rotational speed requirements and RPM-based recipe tiers to processing machines, without replacing Create's original recipe types.

## Features

- Minimum RPM requirements for processing recipes
- Multiple RPM tiers for the same input
- Automatically selects the highest valid recipe tier for the current speed
- Original Create machines and recipe types remain unchanged
- Create kinetic stress integration
- Create Ponder integration
- JEI integration
- Dedicated Creative Mode tab

## How RPM Recipe Selection Works

Recipes can define:

```json
"min_rpm": 64
```

When multiple recipes match the same input, the machine selects the recipe with the highest `min_rpm` that does not exceed the current rotational speed.

| Current Speed | Selected Recipe |
|---:|---|
| 16 RPM | No RPM recipe available |
| 32 RPM | 32 RPM recipe |
| 64 RPM | 64 RPM recipe |
| 100 RPM | 64 RPM recipe |
| 128 RPM | 128 RPM recipe |
| 256 RPM | 128 RPM recipe |

Rotation direction does not matter. The absolute RPM value is used.

---

## RPM Millstone

Registry ID:

```text
createrecipeneedrpm:rpm_millstone
```

Recipe Type:

```text
createrecipeneedrpm:rpm_milling
```

The RPM Millstone reuses Create's original Millstone behavior, including item handling, animation, kinetic behavior, sounds, particles, Flywheel rendering, stress handling, and Ponder scenes.

Only recipe selection is changed.

### Example

```json
{
  "type": "createrecipeneedrpm:rpm_milling",
  "ingredients": [
    {
      "item": "minecraft:cobblestone"
    }
  ],
  "results": [
    {
      "id": "minecraft:gravel",
      "count": 2
    }
  ],
  "processing_time": 100,
  "min_rpm": 64
}
```

The Millstone can accept an item even when the current RPM is below the recipe requirement. Processing pauses when the speed is insufficient and resumes when enough RPM becomes available.

Stress Impact:

```text
4 SU/RPM
```

---

## RPM Mechanical Press

Registry ID:

```text
createrecipeneedrpm:rpm_mechanical_press
```

The RPM Mechanical Press supports RPM Pressing, RPM Compacting, and Create's original Automatic Packing behavior.

### RPM Pressing

Recipe Type:

```text
createrecipeneedrpm:rpm_pressing
```

Used for pressing items on belts, depots, and world items.

```json
{
  "type": "createrecipeneedrpm:rpm_pressing",
  "ingredients": [
    {
      "item": "minecraft:iron_ingot"
    }
  ],
  "results": [
    {
      "id": "minecraft:iron_nugget",
      "count": 3
    }
  ],
  "min_rpm": 64
}
```

### RPM Compacting

Recipe Type:

```text
createrecipeneedrpm:rpm_compacting
```

Used when the RPM Mechanical Press operates above a Basin.

```json
{
  "type": "createrecipeneedrpm:rpm_compacting",
  "ingredients": [
    {
      "item": "minecraft:cobblestone"
    },
    {
      "item": "minecraft:cobblestone"
    }
  ],
  "results": [
    {
      "id": "minecraft:stone",
      "count": 2
    }
  ],
  "min_rpm": 64
}
```

When multiple RPM Compacting recipes match the same Basin contents, the highest available RPM tier is selected.

### Automatic Packing

Create's original Automatic Packing behavior is preserved and does **not** require `min_rpm`.

Normal 2x2 and 3x3 packing recipes continue to work with the RPM Mechanical Press just like they do with Create's original Mechanical Press.

Example:

```text
4x Iron Ingot
     ↓
RPM Mechanical Press + Basin
     ↓
Iron Trapdoor
```

Stress Impact:

```text
8 SU/RPM
```

---

## JEI Integration

Dedicated JEI categories are currently provided for:

- RPM Milling
- RPM Pressing
- RPM Compacting

Each RPM recipe displays its minimum required speed:

```text
Min. RPM: 64
```

The categories reuse Create's existing recipe layouts and animations where possible.

Create's original Automatic Packing JEI category remains available for normal packing recipes.

---

## Ponder Integration

RPM machines reuse Create's original Ponder scenes and localization.

Currently supported:

- RPM Millstone
  - Millstone
- RPM Mechanical Press
  - Pressing
  - Compacting

---

## Kinetic Tooltips

RPM machines integrate with Create's kinetic tooltip system and display:

```text
Kinetic Stress Impact
```

Detailed kinetic information is available through Create's existing systems.

---

## Creative Mode Tab

The mod has its own Creative Mode tab.

Currently included:

- RPM Millstone
- RPM Mechanical Press

More machines will be added as development continues.

---

## Compatibility

The mod intentionally uses separate recipe types instead of replacing Create's original ones.

Original Create recipe types remain untouched:

```text
create:milling
create:pressing
create:compacting
```

RPM-aware machines use:

```text
createrecipeneedrpm:rpm_milling
createrecipeneedrpm:rpm_pressing
createrecipeneedrpm:rpm_compacting
```

This design is intended to reduce conflicts with Create addons and datapacks that depend on the original Create recipe types.

---

## Requirements

- Minecraft 1.21.1
- NeoForge
- Create 6.0.10
- Java 21

JEI integration is available when JEI is installed.

---

## Current Status

| Feature | Status |
|---|---|
| RPM recipe parameter | ✅ |
| RPM tier selection | ✅ |
| RPM Millstone | ✅ |
| RPM Milling | ✅ |
| RPM Mechanical Press | ✅ |
| RPM Pressing | ✅ |
| RPM Compacting | ✅ |
| Automatic Packing | ✅ |
| Kinetic Stress integration | ✅ |
| JEI integration | ✅ |
| Ponder integration | ✅ |
| Creative Mode tab | ✅ |
| RPM Mechanical Mixer | Planned |
| RPM Mixing | Planned |
| RPM Crushing Wheel | Planned |
| RPM Crushing | Planned |

---

## Internal Structure

Shared RPM recipe infrastructure currently includes:

```text
RPMProcessingRecipeParams
RPMRequiredRecipe
RPMRecipeSelector
```

General selection rule:

```text
matching input
AND
min_rpm <= abs(current_rpm)

→ select the matching recipe with the highest min_rpm
```

---

## Planned

### RPM Mechanical Mixer

Planned recipe type:

```text
createrecipeneedrpm:rpm_mixing
```

The goal is to support RPM-aware Mixing while preserving Create's Basin and heat-condition behavior.

### RPM Crushing Wheel

Planned recipe type:

```text
createrecipeneedrpm:rpm_crushing
```

The goal is to add RPM-aware Crushing while keeping Create's original Crushing recipes untouched.

---

## License

TBD
