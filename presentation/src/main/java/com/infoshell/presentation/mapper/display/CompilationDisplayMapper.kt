package com.infoshell.presentation.mapper.display

import com.infoshell.domain.entity.Compilation
import com.infoshell.presentation.enity.DisplayCompilation

class CompilationDisplayMapper : DomainToDisplayMapper<Compilation, DisplayCompilation> {

    override fun mapToDisplay(domainModel: Compilation): DisplayCompilation {
        return DisplayCompilation(name = domainModel.name, images = domainModel.images)
    }
}