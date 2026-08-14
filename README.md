# Create: Recipe Need RPM

[한국어 README](README_KO.md)

CreateRecipeNeedRPM is a Create addon for Minecraft 1.21.1 that adds RPM-aware variants of selected Create machines and processing recipes.

Recipes can define a `min_rpm`, allowing the same input to produce different results depending on machine speed.

## Features

### RPM-aware machines

- RPM Millstone
- RPM Mechanical Press
- RPM Mechanical Mixer
- RPM Crushing Wheel

These are separate machines from the original Create blocks, so existing Create machines and recipes remain untouched.

### RPM-aware recipe types

- `createrecipeneedrpm:rpm_milling`
- `createrecipeneedrpm:rpm_pressing`
- `createrecipeneedrpm:rpm_compacting`
- `createrecipeneedrpm:rpm_mixing`
- `createrecipeneedrpm:rpm_crushing`

Each recipe can define a minimum RPM:

```json
"min_rpm": 64
```

When multiple recipes match the same input, the machine selects the matching recipe with the highest `min_rpm` that does not exceed the machine's current absolute RPM.

Example:

```text
min_rpm 32  -> Result A
min_rpm 64  -> Result B
min_rpm 128 -> Result C
```

At 100 RPM, Result B is selected. There is no maximum RPM condition.

## Machine behavior

### RPM Millstone

Processes `rpm_milling` recipes.

### RPM Mechanical Press

Supports:

- `rpm_pressing`
- `rpm_compacting`
- Create's normal Automatic Packing behavior
- `rpm_pressing` inside Create Sequenced Assembly

Automatic Packing does not require this addon's `min_rpm`; Create's original behavior is preserved.

### RPM Mechanical Mixer

Supports:

- `rpm_mixing`
- Create's normal Shapeless Mixing behavior
- Create's normal Brewing behavior

Automatic Shapeless Mixing and Brewing do not require this addon's `min_rpm`. Create's normal mixer speed requirement still applies.

### RPM Crushing Wheel

Processes `rpm_crushing` recipes only.

Unlike Create's original Crushing Wheels, RPM Crushing Wheels do **not** fall back to Milling recipes.

If no eligible `rpm_crushing` recipe exists at the current RPM, the item is treated as having no valid crushing recipe and can be destroyed, matching Create's original no-recipe crushing behavior.

## Recipe example

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
      "id": "minecraft:diamond"
    }
  ],
  "min_rpm": 64
}
```

## Sequenced Assembly

`rpm_pressing` can be used as a Create Sequenced Assembly step. Each RPM Pressing step may use a different `min_rpm`.

```json
{
  "type": "create:sequenced_assembly",
  "ingredient": {
    "tag": "c:dusts/obsidian"
  },
  "results": [
    {
      "id": "create:sturdy_sheet"
    }
  ],
  "sequence": [
    {
      "type": "createrecipeneedrpm:rpm_pressing",
      "ingredients": [
        {
          "item": "create:unprocessed_obsidian_sheet"
        }
      ],
      "results": [
        {
          "id": "create:unprocessed_obsidian_sheet"
        }
      ],
      "min_rpm": 64
    },
    {
      "type": "createrecipeneedrpm:rpm_pressing",
      "ingredients": [
        {
          "item": "create:unprocessed_obsidian_sheet"
        }
      ],
      "results": [
        {
          "id": "create:unprocessed_obsidian_sheet"
        }
      ],
      "min_rpm": 128
    }
  ],
  "transitional_item": {
    "id": "create:unprocessed_obsidian_sheet"
  }
}
```

JEI displays the RPM requirement above RPM Pressing steps in Sequenced Assembly.

## KubeJS

CreateRecipeNeedRPM works with KubeJS raw custom recipes through `event.custom()` without requiring a dedicated KubeJS addon.

```js
ServerEvents.recipes(event => {
    event.custom({
        type: 'createrecipeneedrpm:rpm_pressing',
        ingredients: [
            {
                item: 'minecraft:iron_ingot'
            }
        ],
        results: [
            {
                id: 'minecraft:diamond'
            }
        ],
        min_rpm: 64
    })
})
```

The same approach works with:

```text
rpm_milling
rpm_pressing
rpm_compacting
rpm_mixing
rpm_crushing
```

`rpm_pressing` can also be used inside a `create:sequenced_assembly` recipe created with `event.custom()`.

## JEI and Ponder

- Dedicated JEI categories for RPM recipes
- Minimum RPM shown directly in recipe displays
- Sequenced Assembly RPM Pressing steps display RPM vertically above the press
- Create Ponder scenes are reused for RPM machines where appropriate

## Stress Impact

| Machine | Stress Impact |
| --- | ---: |
| RPM Millstone | 4 SU/RPM |
| RPM Mechanical Press | 8 SU/RPM |
| RPM Mechanical Mixer | 4 SU/RPM |
| RPM Crushing Wheel | 8 SU/RPM per wheel |

## Requirements

- Minecraft 1.21.1
- NeoForge 21.1.x
- Java 21
- Create 6.0.10

JEI and KubeJS integrations are available when those mods are installed.

## Installation

1. Install NeoForge for Minecraft 1.21.1.
2. Install Create 6.0.10 and its required dependencies.
3. Place the CreateRecipeNeedRPM jar in the `mods` folder.
4. Optionally install JEI and/or KubeJS.

## Compatibility philosophy

CreateRecipeNeedRPM adds separate RPM-aware machines and recipe types instead of replacing Create's original machines and recipes.

The goal is to let modpack and datapack authors build RPM-based progression while preserving normal Create behavior and compatibility with existing Create content as much as possible.

## License

Licensed under the [MIT License](LICENSE).
