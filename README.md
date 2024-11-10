# 시나리오
### 출력
- 등록된 각 단일 제품을 출력
- 바로 하단에 관련 프로모션 출력

### 구매
- 사용자가 구입 할 제품을 입력 받는다.
- 사용자의 입력을 검증한다.

### 프로모션
- 프로모션이 가능한 수량보다 적다면, 이를 더 보충할지 묻는다.
- 각 물품에 대하여 프로모션을 적용하여 `최적으로` 계산한다. 
- 여전히 필요한 제품에 대하여 정가로 결제할지, 아예 제외할지 묻는다.
- 최종 가격에 대하여 멤버십 할인을 제공할지의 여부를 묻는다.

### 영수증
- 구매 및 증정 물품을 저장한다.
- 증정 물품에 따른 행사할인을 계산한다.
- 행사할인에 따른 멤버십할인을 계산한다.
- 최종적으로 지불해야 할 돈을 계산한다.
- 영수증을 출력한다.

### 재구매
- 사용자에게 다시 구매할지 여부를 묻는다.
- 다시 구매한다면 출력 단계로 돌아간다.

# 기능 목록

## Model
- Product
- ProductManager
- SalesProduct
- SalesProductManager
- SalesPolicy
- SalesPolicyManager
- SalesType
- Cart
- CartItem
- Order
- OrderManager
- Receipt
- History

## Controller
- ControllerBroker
- FrontController
- CartController
- OrderController
- PaymentController

## Repository
- CartRepository
- OrderRepository
- ProductRepository
- ReceiptRepository
- SalesPolicyRepository
- SalesProductRepository

## Service
- PaymentService
- ProductService
- SalesPolicyService
- SalesProductService

## Utility
- DataReader
- FlowHandler