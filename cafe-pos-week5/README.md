# Lab 5 
## Decorator vs Factory
```In your README, write three to four sentences explaining which construction approach you would expose to application developers and why```
I would expose the Decorator construction approach to application developers,  
in my opinion it is more clear how it works as it uses explicit function calls rather than strings which factory uses,  
using strings could potentially lead to errors if the API / Factory changes or if they just mistype the wrong string,  
the factory also "hard codes" the prices so you cannot change it without modifiying it, the decorator approach requires the price
to be passed into the base product.
Overall the decorator approach is less error prone, more explicit and preserves the open closed principle, so I would prefer to expose/use that.  


