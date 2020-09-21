package com.App.service.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CarStatistic {
    Statistic statistic;

    public CarStatistic(BigDecimalSummaryStatistics summaryStatistics){
        this.statistic = new Statistic(summaryStatistics.getMin(), summaryStatistics.getMax(), summaryStatistics.getAverage());
    }

}
