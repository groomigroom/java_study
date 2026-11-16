맥(macOS)에서는 Windows의 `ipconfig` 대신 다른 명령어를 사용합니다.

## 1. 내 IP 주소 확인하기

맥 터미널을 열고 다음 명령어를 입력하세요.

내부 IP (Wi-Fi 기준)

bash

```bash
ipconfig getifaddr en0
```

예시 출력:

text

```text
192.168.0.15
```

외부 IP (공인 IP)

bash

```bash
curl ifconfig.me
```

## 2. 네트워크 전체 정보 확인하기

bash

```bash
ifconfig
```

네트워크 인터페이스별 IP, MAC 주소 등의 정보를 확인할 수 있습니다.

## 3. 기본 게이트웨이 확인하기

bash

```bash
route -n get default
```

출력에서 `gateway` 항목을 확인하면 됩니다.

## 자주 사용하는 명령어 정리

| 목적 | 명령어 |
| --- | --- |
| 내부 IP 확인 | `ipconfig getifaddr en0` |
| 공인 IP 확인 | `curl ifconfig.me` |
| 네트워크 전체 정보 | `ifconfig` |
| 게이트웨이 확인 | `route -n get default` |
| DNS 확인 | `scutil --dns` |

참고로 맥에서 Wi-Fi는 일반적으로 `en0`이지만, 환경에 따라 다를 수 있습니다. `en1`일 수도 있으니 확인이 필요하면 `ifconfig`를 사용하세요.
