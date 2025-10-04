package com.example.finup.domain

interface YearMonthStateManager {

    interface InitialGetter {

        suspend fun getInitialYearMonth(): YearMonth
    }

    interface StateSaver {
        suspend fun saveYearMonthState(id: Long)
    }

    interface All : InitialGetter, StateSaver

    class Base(
        private val repository: YearMonthRepository.CreateAndLoad,
        private val yearMonthStateRepository: YearMonthStateRepository,
        private val dateProvider: DateProvider.Getters,
    ) : All {

        override suspend fun getInitialYearMonth(): YearMonth {
            val currentId = yearMonthStateRepository.getActiveYearMonthId()

            if (currentId != 0L) {
                return repository.getById(currentId)

            } else {
                val newYearMonth =
                    repository.create(dateProvider.getCurrentYear(), dateProvider.getCurrentMonth())
                saveYearMonthState(newYearMonth.id)
                return newYearMonth
            }
        }

        override suspend fun saveYearMonthState(id: Long) {
            yearMonthStateRepository.setActiveYearMonthId(id)
        }
    }
}