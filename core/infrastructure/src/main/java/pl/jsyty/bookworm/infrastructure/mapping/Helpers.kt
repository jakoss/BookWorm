// ktlint-disable filename
package pl.jsyty.bookworm.infrastructure.mapping

import org.mapstruct.factory.Mappers

inline fun <reified T> getMapper(): T {
    return Mappers.getMapper(T::class.java)
}
