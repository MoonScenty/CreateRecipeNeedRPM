# Create: Recipe Need RPM

[English README](README.md)

Minecraft 1.21.1 NeoForge용 Create 애드온입니다.

Create의 기존 레시피 타입을 변경하지 않고, 가공 레시피에 최소 회전 속도와 RPM 단계별 레시피를 추가합니다.

## 주요 기능

- 가공 레시피에 최소 RPM 조건 추가
- 동일한 입력에 여러 RPM 단계의 레시피 정의 가능
- 현재 회전 속도에서 사용할 수 있는 가장 높은 RPM 단계 자동 선택
- Create의 기존 기계와 기존 레시피 타입은 그대로 유지
- Create Kinetic Stress 연동
- Create Ponder 연동
- JEI 연동
- 전용 Creative Mode 탭 제공

## RPM 레시피 선택 방식

레시피에 다음 값을 지정할 수 있습니다.

```json
"min_rpm": 64
```

같은 입력에 여러 레시피가 존재할 경우, 현재 RPM 이하의 `min_rpm` 값을 가진 레시피 중 가장 높은 단계를 선택합니다.

| 현재 속도 | 선택되는 레시피 |
|---:|---|
| 16 RPM | 사용 가능한 RPM 레시피 없음 |
| 32 RPM | 32 RPM 레시피 |
| 64 RPM | 64 RPM 레시피 |
| 100 RPM | 64 RPM 레시피 |
| 128 RPM | 128 RPM 레시피 |
| 256 RPM | 128 RPM 레시피 |

회전 방향은 영향을 주지 않으며 절대 RPM 값을 사용합니다.

---

## RPM Millstone

레지스트리 ID:

```text
createrecipeneedrpm:rpm_millstone
```

레시피 타입:

```text
createrecipeneedrpm:rpm_milling
```

RPM Millstone은 Create의 기존 Millstone 동작을 최대한 그대로 재사용합니다.

재사용되는 기능:

- 아이템 입력/출력
- 가공 애니메이션
- Kinetic Network
- 소리 및 파티클
- Flywheel 렌더링
- Stress 시스템
- Ponder 장면

차이는 RPM 기반 레시피 선택 부분입니다.

### 예제 레시피

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

현재 RPM이 요구값보다 낮더라도 아이템은 Millstone 내부로 들어갈 수 있습니다. 속도가 부족하면 가공이 일시 정지되고, 충분한 RPM이 확보되면 다시 진행됩니다.

Stress Impact:

```text
4 SU/RPM
```

---

## RPM Mechanical Press

레지스트리 ID:

```text
createrecipeneedrpm:rpm_mechanical_press
```

RPM Mechanical Press는 RPM Pressing, RPM Compacting, 그리고 Create의 기존 Automatic Packing을 지원합니다.

### RPM Pressing

레시피 타입:

```text
createrecipeneedrpm:rpm_pressing
```

벨트, Depot 또는 바닥 아이템을 압착할 때 사용됩니다.

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

레시피 타입:

```text
createrecipeneedrpm:rpm_compacting
```

RPM Mechanical Press가 Basin 위에서 동작할 때 사용됩니다.

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

같은 Basin 내용물에 여러 RPM Compacting 레시피가 일치하면 현재 속도에서 사용할 수 있는 가장 높은 RPM 단계를 선택합니다.

### Automatic Packing

Create의 기존 Automatic Packing 동작은 그대로 유지되며 `min_rpm`을 요구하지 않습니다.

일반적인 2x2 / 3x3 압축 Crafting Recipe는 Create의 원본 Mechanical Press와 동일하게 사용할 수 있습니다.

예:

```text
철괴 4개
   ↓
RPM Mechanical Press + Basin
   ↓
철 다락문
```

Stress Impact:

```text
8 SU/RPM
```

---

## JEI 연동

현재 다음 RPM 레시피에 전용 JEI 카테고리를 제공합니다.

- RPM Milling
- RPM Pressing
- RPM Compacting

각 레시피 화면에 최소 요구 RPM이 표시됩니다.

```text
최소 RPM: 64
```

가능한 경우 Create의 기존 JEI 레이아웃과 애니메이션을 재사용합니다.

일반 Packing Recipe는 Create의 기존 Automatic Packing JEI 카테고리를 그대로 사용합니다.

---

## Ponder 연동

RPM 기계는 Create의 기존 Ponder 장면과 번역을 재사용합니다.

현재 지원:

- RPM Millstone
  - Millstone
- RPM Mechanical Press
  - Pressing
  - Compacting

---

## Kinetic Tooltip

RPM 기계는 Create의 Kinetic Tooltip 시스템과 연동되며 다음 정보를 표시합니다.

```text
Kinetic Stress Impact
```

세부 Kinetic 정보 역시 Create의 기존 시스템을 사용합니다.

---

## Creative Mode 탭

모드 전용 Creative Mode 탭을 제공합니다.

현재 포함된 기계:

- RPM Millstone
- RPM Mechanical Press

추가 기계가 구현되면 계속 확장할 예정입니다.

---

## 호환성 설계

이 모드는 Create의 기존 레시피 타입을 교체하지 않습니다.

기존 Create 레시피:

```text
create:milling
create:pressing
create:compacting
```

RPM 전용 레시피:

```text
createrecipeneedrpm:rpm_milling
createrecipeneedrpm:rpm_pressing
createrecipeneedrpm:rpm_compacting
```

이 구조는 Create의 기존 레시피 타입에 의존하는 다른 애드온 및 데이터팩과의 충돌 가능성을 줄이기 위한 설계입니다.

---

## 요구 사항

- Minecraft 1.21.1
- NeoForge
- Create 6.0.10
- Java 21

JEI가 설치되어 있을 경우 JEI 연동 기능을 사용할 수 있습니다.

---

## 현재 구현 상태

| 기능 | 상태 |
|---|---|
| RPM 레시피 파라미터 | ✅ |
| RPM 단계 선택 | ✅ |
| RPM Millstone | ✅ |
| RPM Milling | ✅ |
| RPM Mechanical Press | ✅ |
| RPM Pressing | ✅ |
| RPM Compacting | ✅ |
| Automatic Packing | ✅ |
| Kinetic Stress 연동 | ✅ |
| JEI 연동 | ✅ |
| Ponder 연동 | ✅ |
| Creative Mode 탭 | ✅ |
| RPM Mechanical Mixer | 예정 |
| RPM Mixing | 예정 |
| RPM Crushing Wheel | 예정 |
| RPM Crushing | 예정 |

---

## 내부 구조

현재 공통 RPM 레시피 시스템은 다음 구조를 사용합니다.

```text
RPMProcessingRecipeParams
RPMRequiredRecipe
RPMRecipeSelector
```

기본 선택 규칙:

```text
입력 일치
AND
min_rpm <= abs(current_rpm)

→ 조건을 만족하는 레시피 중 min_rpm이 가장 높은 레시피 선택
```

---

## 예정 기능

### RPM Mechanical Mixer

예정 레시피 타입:

```text
createrecipeneedrpm:rpm_mixing
```

Create의 Basin 및 Heat Condition 동작을 유지하면서 RPM 기반 Mixing을 지원하는 것이 목표입니다.

### RPM Crushing Wheel

예정 레시피 타입:

```text
createrecipeneedrpm:rpm_crushing
```

Create의 기존 Crushing Recipe는 유지하면서 별도의 RPM Crushing Recipe를 추가할 예정입니다.

---

## 라이선스

TBD
