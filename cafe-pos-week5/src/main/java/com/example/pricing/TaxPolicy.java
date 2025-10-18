package com.example.pricing;

import com.example.common.Money;

public interface TaxPolicy { Money taxOn(Money amount); }