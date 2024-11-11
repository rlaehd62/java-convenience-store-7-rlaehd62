# 기능목록
### View
#### 사용자 UI 및 상호작용 영역
- Input View
- Output View

### Controller
#### Model과 View 간의 흐름을 제어
- Controller Broker
1. 주문
   - Fron tController
   - Cart Controller
   - Order Controller
   
2. 결제
   - Payment Controller

### Service
#### Controller 내의 비지니스 로직
1. 재고
   - Product Service
   - SalesPolicy Service
   - SalesProduct Service
2. 주문
   - Cart Service
   - Order Service
3. 결제 
   - Payment Service

### Repository
#### Model에 대한 세부적인 데이터 연산을 담당
1. 재고
    - Product Repository
    - SalesPolicy Repository
    - SalesProduct Repository
2. 주문
    - Cart Repository
    - Order Repository
3. 결제
    - Receipt Service
### Model
#### 문제 해결에 필요한 데이터 객체
1. 재고
    - Product Manager
    - Product
    - SalesPolicy Manager
    - SalesPolicy
    - SalesProduct Manager
    - SalesProduct
    - SalesType
    - Price
2. 주문
    - Cart
    - Cart Item
    - Order Manager
    - Order 
3. 결제
    - Receipt
    - History

### Utility
#### 문제 전반에서 유용하게 사용되는 편의성 기능
- Data Reader
- DateTimeUtil
- FlowHandler
- Validator

### Config
#### 시스템 설정 관리 및 의존성 주입을 위한 기능
- System Bean
- Service Bean
- Repository Bean
- System Config
- Error Message
- Data Path
- Message