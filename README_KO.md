# Create: Recipe Need RPM

[English README](README.md)

CreateRecipeNeedRPM은 Minecraft 1.21.1용 Create 애드온으로, 일부 Create 기계와 가공 레시피에 RPM 조건을 추가한 별도 변형을 제공합니다.

레시피에 `min_rpm`을 지정할 수 있으며 동일한 입력이라도 기계의 회전 속도에 따라 서로 다른 결과를 만들 수 있습니다.

## 주요 기능

### RPM 대응 기계

- RPM 맷돌
- RPM 기계식 압축기
- RPM 기계식 믹서
- RPM 분쇄 휠

기존 Create 블록을 직접 변경하지 않고 별도의 기계를 추가하므로 기존 Create 기계와 레시피는 그대로 유지됩니다.

### RPM 대응 레시피 타입

- `createrecipeneedrpm:rpm_milling`
- `createrecipeneedrpm:rpm_pressing`
- `createrecipeneedrpm:rpm_compacting`
- `createrecipeneedrpm:rpm_mixing`
- `createrecipeneedrpm:rpm_crushing`

각 레시피에는 다음과 같이 최소 RPM을 지정할 수 있습니다.

```json
"min_rpm": 64
```

동일한 입력에 여러 레시피가 존재하면 현재 기계의 절대 RPM 이하에서 사용할 수 있는 레시피 중 `min_rpm`이 가장 높은 레시피를 선택합니다.

예:

```text
min_rpm 32  -> 결과 A
min_rpm 64  -> 결과 B
min_rpm 128 -> 결과 C
```

현재 속도가 100 RPM이면 64 RPM 레시피가 선택됩니다. 최대 RPM 조건은 없습니다.

## 기계별 동작

### RPM 맷돌

`rpm_milling` 레시피를 처리합니다.

### RPM 기계식 압축기

다음을 지원합니다.

- `rpm_pressing`
- `rpm_compacting`
- Create 기본 Automatic Packing
- Create Sequenced Assembly 내부의 `rpm_pressing`

Automatic Packing에는 본 모드의 `min_rpm` 조건을 적용하지 않으며 Create 기본 동작을 유지합니다.

### RPM 기계식 믹서

다음을 지원합니다.

- `rpm_mixing`
- Create 기본 Shapeless Mixing
- Create 기본 Brewing

자동 Shapeless Mixing 및 Brewing에는 본 모드의 `min_rpm` 조건을 적용하지 않습니다. 단, Create 기본 믹서의 속도 요구사항은 그대로 적용됩니다.

### RPM 분쇄 휠

`rpm_crushing` 레시피만 처리합니다.

Create 기본 Crushing Wheel과 달리 Milling 레시피 fallback을 사용하지 않습니다.

현재 RPM에서 처리 가능한 `rpm_crushing` 레시피가 없다면 유효한 분쇄 레시피가 없는 것으로 처리되며, Create 기본 Crushing Wheel의 no-recipe 동작과 마찬가지로 투입 아이템이 소실될 수 있습니다.

## 레시피 예제

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

`rpm_pressing`은 Create의 Sequenced Assembly 단계로 사용할 수 있으며 각 단계마다 서로 다른 `min_rpm`을 지정할 수 있습니다.

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

JEI의 Sequenced Assembly 화면에서는 RPM Pressing 단계 위에 필요한 RPM이 표시됩니다.

## KubeJS

별도의 KubeJS 전용 애드온 없이 `event.custom()`으로 CreateRecipeNeedRPM 레시피를 추가할 수 있습니다.

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

같은 방식으로 다음 레시피를 사용할 수 있습니다.

```text
rpm_milling
rpm_pressing
rpm_compacting
rpm_mixing
rpm_crushing
```

또한 `event.custom()`으로 만든 `create:sequenced_assembly` 내부에서도 `rpm_pressing`을 사용할 수 있습니다.

## JEI 및 Ponder

- RPM 레시피 전용 JEI 카테고리 제공
- 레시피 화면에 최소 RPM 표시
- Sequenced Assembly의 RPM Pressing 단계 위에 RPM을 세로 형태로 표시
- 가능한 경우 Create의 기존 Ponder 장면 재사용

## Stress Impact

| 기계 | Stress Impact |
| --- | ---: |
| RPM 맷돌 | 4 SU/RPM |
| RPM 기계식 압축기 | 8 SU/RPM |
| RPM 기계식 믹서 | 4 SU/RPM |
| RPM 분쇄 휠 | 휠 하나당 8 SU/RPM |

## 요구사항

- Minecraft 1.21.1
- NeoForge 21.1.x
- Java 21
- Create 6.0.10

JEI와 KubeJS가 설치되어 있는 경우 해당 연동 기능을 사용할 수 있습니다.

## 설치

1. Minecraft 1.21.1용 NeoForge를 설치합니다.
2. Create 6.0.10과 Create의 필수 의존성을 설치합니다.
3. CreateRecipeNeedRPM jar 파일을 `mods` 폴더에 넣습니다.
4. 필요하다면 JEI 및 KubeJS를 추가로 설치합니다.

## 호환성 방향

CreateRecipeNeedRPM은 Create의 기존 기계와 레시피를 직접 대체하지 않고 별도의 RPM 대응 기계와 레시피 타입을 추가하는 방식으로 설계되었습니다.

기존 Create 콘텐츠를 최대한 유지하면서 모드팩 및 데이터팩 제작자가 RPM 기반 진행 구조를 만들 수 있도록 하는 것이 목적입니다.

## 라이선스

[MIT License](LICENSE)를 따릅니다.
