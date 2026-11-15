전자정부표준프레임워크(eGovFramework)에서 RTE Distribution Tool(RDT)은 실행환경 배포 도구(Runtime Environment Distribution Tool)를 의미합니다. [1] (https://egovframe.go.kr/eng/ntt/nttRead.do?menuNo=21&bbsId=101&nttId=1427), [2] (https://www.egovframe.go.kr/)
개발자가 애플리케이션을 실행하는 데 필요한 표준프레임워크 실행환경(RTE) 라이브러리를 손쉽게 구성하고 프로젝트에 배포·적용할 수 있도록 지원하는 이클립스(Eclipse) 기반의 개발 도구입니다. [1] (https://www.egovframe.go.kr/docs/5.0/egovframe-development/implementation-tool/), [2] (https://egovframe.go.kr/eng/ntt/nttRead.do?menuNo=21&bbsId=101&nttId=1427)

주요 역할 및 의미

• 실행환경(RTE) 라이브러리 패키지 관리. 전자정부표준프레임워크는 화면처리, 업무처리, 데이터처리 등 기능별로 수많은 라이브러리(egovframework.rte.*)를 제공합니다. RDT는 이 라이브러리들의 버전과 의존성을 체계적으로 묶어 관리하는 역할을 합니다. [1] (https://www.egovframe.go.kr/home/sub.do?menuNo=25), [2] (https://www.egovframe.go.kr/eng/ntt/nttRead.do?pagerOffset=0&maxPageItems=10&maxIndexPages=5&searchKey=&searchValue=&menuNo=19&bbsId=100&nttId=1861), [3] (https://www.egovframe.go.kr/eng/ntt/nttRead.do?pagerOffset=0&searchKey=&searchValue=&menuNo=19&bbsId=100&nttId=1857), [4] (https://egovframe.go.kr/eng/ntt/nttRead.do?menuNo=21&bbsId=101&nttId=1427)
• 프로젝트 적용 및 배포 편의성 제공. 개발자가 일일이 수동으로 필요한 라이브러리(JAR 파일 등)를 찾거나 설정하지 않아도, 개발 도구(IDE) 내에서 프로젝트에 필요한 실행환경 구성을 손쉽게 선택하여 적용할 수 있게 해줍니다. [1] (https://egovframe.go.kr/eng/ntt/nttRead.do?menuNo=21&bbsId=101&nttId=1427), [2] (https://www.egovframe.go.kr/docs/5.0/egovframe-development/implementation-tool/)
• 개발환경(Implementation Tool)의 핵심 구성요소. 표준프레임워크 포털에서 배포하는 개발자용 개발환경(Implementation Tool) 패키지에는 코드 에디터, 프로젝트 생성 마법사, 모바일 IDE, 배치 IDE 등과 함께 RDT가 기본 플러그인 형태로 포함되어 있습니다. [1] (https://egovframe.go.kr/eng/ntt/nttRead.do?menuNo=21&bbsId=101&nttId=1427), [2] (https://www.egovframe.go.kr/docs/5.0/egovframe-development/implementation-tool/)

왜 중요한가요?

• 버전 호환성 문제 방지. 실행환경 버전이 업그레이드될 때(예: Spring 프레임워크 버전 변경 등) 기존 라이브러리와 충돌이 발생할 수 있습니다. RDT를 통해 표준화된 실행환경 배포 구성을 따르면 검증된 호환성을 유지할 수 있습니다. [1] (https://www.egovframe.go.kr/eng/ntt/nttRead.do?pagerOffset=0&maxPageItems=10&maxIndexPages=5&searchKey=&searchValue=&menuNo=19&bbsId=100&nttId=1861), [2] (https://www.youtube.com/watch?v=dqqpcw0l6q0&t=125)
• 경량화된 환경 구성. 시스템 규모나 성격에 따라 모든 실행환경 기능이 필요하지 않을 때, 필수 기능 위주로 실행환경을 선택·구성하여 프로젝트를 가볍게 유지할 수 있도록 돕습니다. [1] (https://www.egovframe.go.kr/docs/5.0/egovframe-development/implementation-tool/), [2] (https://www.youtube.com/watch?v=rLsuNWIrFFQ&t=29)
혹시 특정 버전의 표준프레임워크 개발환경 설치나 실행환경(RTE) 라이브러리 의존성 설정 중 겪고 계신 문제가 있으신가요? 구체적인 상황을 알려주시면 해결 방법을 안내해 드리겠습니다.
