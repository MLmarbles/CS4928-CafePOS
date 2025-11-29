During this lab we removed serveral code smells from ordermanagergod including long methods, primitive obsession, duplicated logic, feature envy and global states.  
This could be seen in places like where the dicount policy logic was located. We made a seperate class for that and were able to hide the logic making the implementation cleaner.   
Another area where me cleaned up code would be with printing receipts.  
We had also simplified conditionals with payment options with paymentstartegy.  
Global variables were also taken out to follow SOLID principles. More specifically dependency inversion principles where we should depend on abstractions and not concrete details.  
Other principles such as single responsibility principles were also followed where we divided logic into classes with clear responsibilities.  
And within these classes like DiscountPolicy and TaxPolicy we are following open closed principles where its open for extension but closed for modification.  
As well as this we are following Liskov Substituion policy where our payment strategy class we replaced the conditional logic with is interchangable.  
If we wanted to add a new discount type we could use the discount policy interface and pass it into pricing service.  


Week8 
Command decouples UI from business logic in PosRemote letting it trigger generic Command objects instead of calling specific methods,
so the UI doesnt need to know about the busines logic behind the button just that it executes the command

Week9
By having all composite methods throw exceptions we are choosing safety as this prevents accidental calls on leaf nodes, though we are sacrificing some transparency as not every sub class suppports every operation.
When adding a new stage like preparing or ready I can just add a new state class instead of writing long conditionals.

Week 10
README trade-off note (Layering vs Partitioning

